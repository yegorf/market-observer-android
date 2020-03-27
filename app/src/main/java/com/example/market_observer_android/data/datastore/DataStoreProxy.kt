package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import io.reactivex.Observable

class DataStoreProxy(
    private var localDataStore: LocalDataStore,
    private var remoteDataStore: RemoteDataStore
) {

    fun getAllLinks(): Observable<List<ActiveLink>> {
        return localDataStore.getAllLinks()
    }

    fun addLink(link: Link) {
        localDataStore.addLink(link)
    }

    fun deleteLink(url: String) {
        localDataStore.deleteLink(url)
    }
}