package com.example.market_observer_android.domain.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.injection.DaggerDomainComponent
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.domain.notification.NotificationHelper
import com.example.market_observer_android.domain.util.MarketParser
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
    private val subscriptions = mutableMapOf<String, Disposable>()
    private val parser = MarketParser()

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
        registerBus()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun onResultsFound(url: String, results: List<LinkResult>) {
        val newResults = mutableListOf<LinkResult>()
        val subscribe = repository.getResults(url)
            .subscribe {
                results.forEach { result ->
                    if (!it!!.contains(result)) {
                        newResults.add(result)
                    }
                }

                if (newResults.isNotEmpty()) {
                    bus.sendData(Event.FIND_RESULTS, newResults)
                    repository.addResults(url, newResults)

                    if (PreferenceManager.isNotificationsOn()) {
                        NotificationHelper(applicationContext as Context)
                            .sendResultNotification(
                                "New results found!",
                                "Found ${newResults.size} results!"
                            )
                    }
                }
            }
    }

    private fun registerBus() {
        busDisposables.add(
            bus.listenData(Event.ADD_LINK_TO_OBSERVE, Link::class.java)
                .subscribe {
                    subscriptions[it.url as String] =
                        Observable.interval(it.periodicity.toLong(), TimeUnit.SECONDS)
                            .subscribe { _ ->
                                try {
                                    val results = parser.parseUrl(it.url as String)
                                    onResultsFound(it.url as String, results)
                                } catch (e: Exception) {
                                    Log.e(tag, e.message!!)
                                }
                            }
                }
        )

        busDisposables.add(
            bus.listenData(Event.REMOVE_LINK_FROM_OBSERVE, String::class.java)
                .subscribe {
                    subscriptions[it]?.dispose()
                    subscriptions.remove(it)
                }
        )

        busDisposables.add(
            bus.listenEvent(Event.REMOVE_ALL_LINK_FROM_OBSERVE)
                .subscribe {
                    subscriptions.forEach {
                        it.value.dispose()
                    }
                    subscriptions.clear()
                }
        )

/*        bus.listenData(Event.PAUSE_LINK_OBSERVE, String::class.java)
            .subscribe {
                subscriptions[it]?.unsubscribe()
            }

        bus.listenData(Event.COUNTINUE_LINK_OBSERVE, Link::class.java)
            .subscribe {
                if (subscriptions.containsKey(it.url) && subscriptions[it.url]!!.isUnsubscribed) {
                    subscriptions[it.url] =
                        Observable.interval(it.periodicity.toLong(), TimeUnit.MINUTES)
                            .subscribe { _ ->
                                parser.parseUrl(it.url)
                            }
                }
            }*/
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
        unsubscribeBus()
    }

    private fun unsubscribeBus() {
        busDisposables.clear()
    }
}