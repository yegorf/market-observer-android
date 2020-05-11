package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.data.firebase.FirebaseService
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable

class RemoteDataStore(
    private val firebaseService: FirebaseService, private val mapper: MapperFactory
) : DataStore {

    fun signUp(email: String, password: String): Observable<Boolean> {
        return firebaseService.signUp(email, password)
    }

    fun signIn(email: String, password: String): Observable<Boolean> {
        return firebaseService.signIn(email, password)
    }

    override fun addSavedResult(result: LinkResult) {
        firebaseService.addSavedResult(mapper.resultToSavedEntityMapper().transform(result))
    }

    override fun getSavedResults(): Observable<List<LinkResult>> {
        return firebaseService.getSavedResults()
    }

    override fun deleteSavedResults(result: LinkResult) {
        firebaseService.deleteSavedResult(result)
    }

    fun saveSettings(settings: SettingsEntity) {
        firebaseService.saveSettings(settings)
    }

    fun getSettings(): Observable<SettingsEntity> {
        return firebaseService.getSettings()
    }

    fun getLinks(): Observable<List<Link>> {
        return firebaseService.getAllLinks()
            .map { list ->
                list.map {
                    mapper.entityToLinkMapper().transform(it)
                }
            }
    }

    fun addLink(link: Link) {
        firebaseService.addLink(mapper.linkToEntityMapper().transform(link))
    }

    fun deleteLink(url: String) {
        firebaseService.deleteLink(url)
    }

    fun addResults(url: String, results: List<LinkResult>) {
        firebaseService.addResults(url, results)
    }
}