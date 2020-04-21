package com.example.market_observer_android.data.datastore

import android.util.Log
import com.example.market_observer_android.data.local.realm_entity.SavedResultRealm
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable

class DataStoreProxy(
    private var localDataStore: LocalDataStore,
    private var remoteDataStore: RemoteDataStore
) {

    fun getAllLinks(): Observable<List<Link>> {
        return localDataStore.getAllLinks()
    }

    fun addLink(link: Link) {
        localDataStore.addLink(link)
    }

    fun deleteLink(url: String) {
        localDataStore.deleteLink(url)
    }

    fun addResults(url: String, results: List<LinkResult>) {
        localDataStore.addResults(url, results)
    }

    fun getResults(url: String): Observable<List<LinkResult>?> {
        return localDataStore.getResults(url)
    }

    fun signUp(email: String, password: String): Observable<Boolean> {
        return remoteDataStore.signUp(email, password)
    }

    fun signIn(email: String, password: String): Observable<Boolean> {
        return remoteDataStore.signIn(email, password)
    }

    fun addSavedResult(result: LinkResult) {
        return localDataStore.addSavedResult(result)
    }

    fun getSavedResults(): Observable<List<LinkResult>> {
        return localDataStore.getSavedResults()
    }
}