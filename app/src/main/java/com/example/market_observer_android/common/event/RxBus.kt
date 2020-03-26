package com.example.market_observer_android.common.event

import rx.Observable
import rx.subjects.PublishSubject

object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun sendEvent(event: Event) {
        publisher.onNext(event)
    }

    fun sendData(event: Event, data: Any) {
        publisher.onNext(DataEvent(event, data))
    }

    fun listenEvent(event: Event): Observable<Event> {
        return publisher.ofType(Event::class.java)
            .filter { it == event }
    }

    //todo: add data type check
    fun <T> listenData(event: Event, type: Class<T>): Observable<T> {
        return publisher.ofType(DataEvent::class.java)
            .filter { it.event == event }
            .map { it.data }
            .cast(type)
    }

    private data class DataEvent(
        var event: Event,
        var data: Any
    )
}