package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.datastore.DataStoreProxy
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.mapper.MapperFactory
import io.reactivex.Observable
import okhttp3.ResponseBody

class RepositoryImpl(var dataStore: DataStoreProxy, var mapper: MapperFactory) : Repository {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return dataStore.login(credentialsEntity)
    }
}