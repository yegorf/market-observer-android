package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.common.exception.NotImplementedException
import com.example.market_observer_android.data.entity.CredentialsEntity
import okhttp3.ResponseBody
import rx.Observable

class LocalDataStore : DataStore {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
       throw NotImplementedException("login")
    }
}