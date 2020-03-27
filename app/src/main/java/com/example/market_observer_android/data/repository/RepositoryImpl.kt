package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import io.reactivex.Observable
import okhttp3.ResponseBody

class RepositoryImpl(var dataStore: DataStoreProxy, var mapper: MapperFactory) : Repository {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return Observable.empty()//dataStore.login(credentialsEntity)
    }

    override fun getActiveLinks(): Observable<List<ActiveLink>> {
        return dataStore.getAllLinks()
    }

    override fun addLink(link: Link) {
        dataStore.addLink(link)
    }
}