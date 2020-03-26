package com.example.market_observer_android.domain.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.market_observer_android.domain.model.Link
import rx.subscriptions.CompositeSubscription

class MonitoringService : Service() {

    companion object {
        var isAlive = false
    }

    private val subscriptions: CompositeSubscription = CompositeSubscription()

    fun observeLink(link: Link) {
//        val subscribe = Observable.interval(link.periodicity.toLong(), TimeUnit.MINUTES)
//            .subscribe {
//                parseUrl(link.url)
//            }
//        subscriptions.add(subscribe)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}