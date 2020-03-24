package com.example.market_observer_android.domain.usecase

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions


abstract class BaseUseCase<T> {

    private var subscription = Subscriptions.empty()

    fun execute(subscriber: Subscriber<T>) {
        subscription = getObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)
    }

    fun stopExecution() {
        if (!subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

    abstract fun getObservable(): Observable<T>
}

