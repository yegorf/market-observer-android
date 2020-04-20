package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.local.RealmService
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.data.local.realm_entity.SavedResultRealm
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable


class LocalDataStore(private val realmService: RealmService, private val mapper: MapperFactory) {

    fun getAllLinks(): Observable<List<Link>> {
        return realmService.getAllLinks()
            .map {
                mapper.realmsToListMapper(it)
            }
            .map {
                mapper.realmLinkListMapper().transform(it)
            }
    }

    fun addLink(link: Link) {
        realmService.addLink(mapper.linkToRealmMapper().transform(link))
    }

    fun deleteLink(url: String) {
        realmService.deleteLink(url)
    }

    fun addResults(url: String, results: List<LinkResult>) {
        val realms = mutableListOf<LinkResultRealm>()
        results.forEach {
            realms.add(mapper.resultToRealmMapper().transform(it))
        }
        realmService.addResults(url, realms)
    }

    fun getResults(url: String): Observable<List<LinkResult>?> {
        return realmService.getResults(url)
            .map {
                mapper.realmLinkResultListMapper().transform(it)
            }
    }

    fun addSavedResult(result: LinkResult) {
        realmService.addSavedResult(SavedResultRealm.fromLinkResult(result))
    }

    fun getSavedResults(): Observable<List<LinkResult>> {
        return realmService.getSavedResults()
            .flatMapIterable { it }
            .map { it.toLinkResult() }
            .toList()
            .toObservable()
    }
}