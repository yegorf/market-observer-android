package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.domain.util.PreferenceManager
import io.reactivex.Observable

class DataStoreProxy(
    private var localDataStore: LocalDataStore,
    private var remoteDataStore: RemoteDataStore
) : DataStore {

    fun getAllLinks(): Observable<List<Link>> {
        return localDataStore.getAllLinks()
        //return remoteDataStore.getLinks()
    }

    fun addLink(link: Link) {
        localDataStore.addLink(link)
        remoteDataStore.addLink(link)
    }

    fun deleteLink(url: String) {
        localDataStore.deleteLink(url)
        remoteDataStore.deleteLink(url)
    }

    fun addResults(url: String, results: List<LinkResult>) {
        localDataStore.addResults(url, results)
        //remoteDataStore.updateResults(url, results)
    }

    fun getResults(url: String): Observable<List<LinkResult>> {
        return localDataStore.getResults(url)
    }

    fun signUp(email: String, password: String): Observable<Boolean> {
        return remoteDataStore.signUp(email, password)
    }

    fun signIn(email: String, password: String): Observable<Boolean> {
        return remoteDataStore.signIn(email, password)
    }

    override fun addSavedResult(result: LinkResult) {
        localDataStore.addSavedResult(result)
        remoteDataStore.addSavedResult(result)
    }

    override fun getSavedResults(): Observable<List<LinkResult>> {
        return localDataStore.getSavedResults()
        //return remoteDataStore.getSavedResults()
    }

    override fun deleteSavedResults(result: LinkResult) {
        localDataStore.deleteSavedResults(result)
        remoteDataStore.deleteSavedResults(result)
    }

    fun saveSettings(settings: SettingsEntity) {
        remoteDataStore.saveSettings(settings)
    }

    fun getSettings(): Observable<SettingsEntity> {
        return remoteDataStore.getSettings()
    }
}