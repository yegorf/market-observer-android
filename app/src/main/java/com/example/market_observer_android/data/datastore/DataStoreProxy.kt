package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.entity.CredentialsEntity
import okhttp3.ResponseBody
import rx.Observable

class DataStoreProxy(var localDataStore: LocalDataStore, var remoteDataStore: RemoteDataStore) :
    DataStore {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return remoteDataStore.login(credentialsEntity)
    }
}