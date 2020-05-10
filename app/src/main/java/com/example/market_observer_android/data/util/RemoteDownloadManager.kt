package com.example.market_observer_android.data.util

import com.example.market_observer_android.data.datastore.LocalDataStore
import com.example.market_observer_android.data.datastore.RemoteDataStore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class RemoteDownloadManager(
    private val localDataStore: LocalDataStore,
    private val remoteDataStore: RemoteDataStore
) {

    private val disposables = CompositeDisposable()

    fun downloadRemote(): Observable<String> {
        val subject = PublishSubject.create<String>()
        downLoadSaved(subject)
        downLoadLinks(subject)
        return subject
    }

    private fun downLoadSaved(subject: PublishSubject<String>) {
        val subscribe = remoteDataStore.getSavedResults()
            .subscribe { saved ->
                saved?.forEach {
                    localDataStore.addSavedResult(it)
                    subject.onNext("saved")
                }
            }
        disposables.add(subscribe)
    }

    private fun downLoadLinks(subject: PublishSubject<String>) {
        val subscribe = remoteDataStore.getLinks()
            .subscribe { links ->
                links.forEach {
                    localDataStore.addLink(it)
                    subject.onNext("links")
                }
            }
        disposables.add(subscribe)
    }
}