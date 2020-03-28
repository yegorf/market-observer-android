package com.example.market_observer_android.common.util

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class UseCaseObserver<T> : Observer<T> {

    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {

    }
}