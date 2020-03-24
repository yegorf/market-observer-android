package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import okhttp3.ResponseBody
import rx.Observable

class RepositoryImpl(var dataStore: DataStoreProxy, var mapper: MapperFactory) : Repository {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return dataStore.login(credentialsEntity)
    }

    override fun getActiveLinks(): Observable<List<ActiveLink>> {
        return Observable.just(
            listOf(
                ActiveLink(Link("www.lol.com", "iphone 11", 10), listOf()),
                ActiveLink(Link("www.kek.com", "iphone 12", 5), listOf()),
                ActiveLink(Link("www.heh.com", "iphone 13", 15), listOf())
            )
        )
    }
}