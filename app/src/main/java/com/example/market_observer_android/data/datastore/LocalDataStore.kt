package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.local.RealmService
import com.example.market_observer_android.data.local.realm_entity.SavedResultRealm
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable


class LocalDataStore(private val realmService: RealmService, private val mapper: MapperFactory) :
    DataStore {

    fun getAllLinks(): Observable<List<Link>> {
        return realmService.getAllLinks()
            .map {
                mapper.mapRealmListToList(it)
            }
            .map { list ->
                list.map {
                    mapper.realmLinkToLinkMapper().transform(it)
                }
            }
    }

    fun addLink(link: Link) {
        realmService.addLink(mapper.linkToRealmMapper().transform(link))
    }

    fun deleteLink(url: String) {
        realmService.deleteLink(url)
    }

    fun addResults(url: String, results: List<LinkResult>) {
        realmService.addResults(url,
            results.map {
                mapper.resultToRealmMapper().transform(it)
            })
    }

    fun getResults(url: String): Observable<List<LinkResult>> {
        return realmService.getResults(url)
            .map { list ->
                list.map {
                    mapper.realmLinkResultMapper().transform(it)
                }
            }
    }

    override fun addSavedResult(result: LinkResult) {
        realmService.addSavedResult(SavedResultRealm.fromLinkResult(result))
    }

    override fun getSavedResults(): Observable<List<LinkResult>> {
        return realmService.getSavedResults()
            .map { list ->
                list.map {
                    it.toLinkResult()
                }
            }
    }

    override fun deleteSavedResults(result: LinkResult) {
        realmService.deleteSavedResult(result)
    }
}