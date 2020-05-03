package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class RepositoryImpl(var dataStore: DataStoreProxy, var mapper: MapperFactory) : Repository {

    private val tag = RepositoryImpl::class.java.simpleName

    override fun signUp(email: String, password: String): Observable<Boolean> {
        return dataStore.signUp(email, password)
    }

    override fun signIn(email: String, password: String): Observable<Boolean> {
        return dataStore.signIn(email, password)
    }

    override fun getActiveLinks(): Observable<List<Link>> {
        return dataStore.getAllLinks()
    }

    override fun addLink(link: Link) {
        dataStore.addLink(link)
    }

    override fun deleteLink(url: String) {
        dataStore.deleteLink(url)
    }

    override fun addResults(url: String, results: List<LinkResult>) {
        dataStore.addResults(url, results)
    }

    override fun getResultsWithSaved(url: String): Observable<List<LinkResult>> {
        return dataStore.getResults(url)
            .zipWith(dataStore.getSavedResults(),
                BiFunction { results: List<LinkResult>, saved: List<LinkResult> ->
                    results.map {
                        it.isSaved = saved.contains(it)
                        return@map it
                    }
                    return@BiFunction results
                })
    }

    override fun getResults(url: String): Observable<List<LinkResult>> {
        return dataStore.getResults(url)
    }

    override fun addSavedResult(result: LinkResult) {
        dataStore.addSavedResult(result)
    }

    override fun getSavedResults(): Observable<List<LinkResult>> {
        return dataStore.getSavedResults()
    }

    override fun deleteSavedResults(result: LinkResult) {
        dataStore.deleteSavedResults(result)
    }

    override fun saveSettings(settings: SettingsEntity) {
        dataStore.saveSettings(settings)
    }

    override fun getSettings(): Observable<SettingsEntity> {
        return dataStore.getSettings()
    }
}