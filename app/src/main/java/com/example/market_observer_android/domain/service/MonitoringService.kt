package com.example.market_observer_android.domain.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.market_observer_android.R
import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.injection.DaggerDomainComponent
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.domain.notification.NotificationHelper
import com.example.market_observer_android.domain.parser.MarketParserFactory
import com.example.market_observer_android.domain.util.PreferenceManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MonitoringService : Service() {

    @Inject
    lateinit var repository: Repository

    private val tag = MonitoringService::class.java.simpleName
    private val bus = RxBus
    private val busDisposables = CompositeDisposable()
    private val repositoryDisposables = CompositeDisposable()
    private val subscriptions = mutableMapOf<String, Disposable>()

    companion object {
        fun startService(context: Context) {
            val monitoringIntent = Intent(context, MonitoringService::class.java)
            context.startService(monitoringIntent)
        }

        fun stopService(context: Context) {
            val monitoringIntent = Intent(context, MonitoringService::class.java)
            context.stopService(monitoringIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startObserve()
        registerBus()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun onResultsFound(url: String, results: List<LinkResult>) {
        val subscribe = repository.getResults(url)
            .subscribe {
                val newResults = results.filter { result ->
                    !it.contains(result)
                }

                if (newResults.isNotEmpty()) {
                    bus.sendData(Event.FIND_RESULTS, newResults)
                    repository.addResults(url, newResults)

                    if (PreferenceManager.isNotificationsOn()) {
                        NotificationHelper(applicationContext as Context)
                            .sendResultNotification(
                                String.format(
                                    getString(R.string.new_results_found),
                                    newResults.size
                                ),
                                String.format(
                                    getString(R.string.found_results_count),
                                    newResults.size,
                                    url
                                )
                            )
                    }
                }
            }
        repositoryDisposables.add(subscribe)
    }

    private fun startObserve() {
        val subscribe = repository.getActiveLinks()
            .subscribe { list ->
                list.filter { it.isActive }
                    .forEach { addLinkToObserve(it) }
            }
        repositoryDisposables.add(subscribe)
    }

    private fun addLinkToObserve(link: Link) {
        val url = link.url
        val periodicity = link.periodicity.toLong()
        if (url != null) {
            subscriptions[url] =
                Observable.interval(periodicity, TimeUnit.SECONDS)
                    .subscribe {
                        try {
                            val results = MarketParserFactory.createParser(url)
                                ?.parseUrl(url)
                            if (results != null) {
                                onResultsFound(url, results)
                            }
                        } catch (e: Exception) {
                            Log.e(tag, e.message!!)
                        }
                    }
        }
    }

    private fun removeLinkFromObserve(url: String) {
        subscriptions[url]?.dispose()
        subscriptions.remove(url)
    }

    private fun registerBus() {
        busDisposables.add(
            bus.listenData(Event.ADD_LINK_TO_OBSERVE, Link::class.java)
                .subscribe { addLinkToObserve(it) }
        )

        busDisposables.add(
            bus.listenData(Event.REMOVE_LINK_FROM_OBSERVE, String::class.java)
                .subscribe { removeLinkFromObserve(it) }
        )
    }

    override fun onCreate() {
        super.onCreate()
        DaggerDomainComponent.builder()
            .dataModule(DataModule())
            .build()
            .inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        busDisposables.clear()
        repositoryDisposables.clear()
    }
}