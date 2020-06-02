package com.example.market_observer_android.domain.usecase

import com.example.market_observer_android.common.util.UseCaseObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables

abstract class BaseUseCase<T> {

    var disposable = Disposables.empty()

    fun execute(observer: UseCaseObserver<T>) {
        getObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                disposable = it
            }
            .doOnNext {
                observer.onNext(it)
            }
            .subscribe(observer)
    }

    fun stop() {
        if (disposable.isDisposed) {
            disposable.dispose()
        }
    }

    protected abstract fun getObservable(): Observable<T>
}