package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.rest.RestApi
import okhttp3.ResponseBody
import rx.Observable

class RemoteDataStore(val restApi: RestApi) : DataStore {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return restApi.login(credentialsEntity)
    }
}