package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.local.RealmService
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import io.reactivex.Observable


class LocalDataStore(private val realmService: RealmService, private val mapper: MapperFactory) {

    fun getAllLinks(): Observable<List<ActiveLink>> {
        return realmService.getAllLinks()
            .map {
                mapper.realmsToListMapper(it)
            }
            .map {
                mapper.realmLinkListMapper().transform(it)
            }
    }

    fun addLink(link: Link) {
        realmService.addLink(link.url, link.name, link.periodicity)
    }
}