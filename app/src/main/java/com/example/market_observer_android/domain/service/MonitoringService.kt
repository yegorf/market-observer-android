package com.example.market_observer_android.domain.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.domain.model.Link
import rx.Observable
import rx.Subscription
import java.util.concurrent.TimeUnit

class MonitoringService : Service() {

    private val bus = RxBus
    private val subscriptions = mutableMapOf<String, Subscription>()

    companion object {
        var isAlive = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun registerBus() {
        bus.listenData(Event.ADD_LINK_TO_OBSERVE, Link::class.java)
            .subscribe {
                subscriptions[it.url] =
                    Observable.interval(it.periodicity.toLong(), TimeUnit.MINUTES)
                        .subscribe {
                            //todo monitoring logic
                        }
            }

        bus.listenData(Event.REMOVE_LINK_FROM_OBSERVE, String::class.java)
            .subscribe {
                subscriptions[it]?.unsubscribe()
                subscriptions.remove(it)
            }

        bus.listenData(Event.PAUSE_LINK_OBSERVE, String::class.java)
            .subscribe {
                subscriptions[it]?.unsubscribe()
            }

        bus.listenData(Event.COUNTINUE_LINK_OBSERVE, Link::class.java)
            .subscribe {
                if (subscriptions.containsKey(it.url) && subscriptions[it.url]!!.isUnsubscribed) {
                    subscriptions[it.url] =
                        Observable.interval(it.periodicity.toLong(), TimeUnit.MINUTES)
                            .subscribe {
                                //todo monitoring logic
                            }
                }
            }
    }
}