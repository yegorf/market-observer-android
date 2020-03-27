package com.example.market_observer_android.domain.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.domain.model.Link
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MonitoringService : Service() {

    private val bus = RxBus
    private val subscriptions = mutableMapOf<String, Disposable>()
    private val parser =
        MarketParser()

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
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun registerBus() {
        bus.listenData(Event.ADD_LINK_TO_OBSERVE, Link::class.java)
            .subscribe {
                subscriptions[it.url as String] =
                    Observable.interval(it.periodicity.toLong(), TimeUnit.SECONDS)
                        .subscribe { _ ->
                            val results = parser.parseUrl(it.url as String)
                            bus.sendData(Event.FIND_RESULTS, results)
                        }
            }

        bus.listenData(Event.REMOVE_LINK_FROM_OBSERVE, String::class.java)
            .subscribe {
                subscriptions[it]?.dispose()
                subscriptions.remove(it)
            }

        bus.listenEvent(Event.REMOVE_ALL_LINK_FROM_OBSERVE)
            .subscribe {
                subscriptions.forEach {
                    it.value.dispose()
                }
                subscriptions.clear()
            }

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
}