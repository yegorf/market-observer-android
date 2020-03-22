package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.common.NotImplementedException
import com.example.market_observer_android.data.entity.CredentialsEntity
import io.reactivex.Observable
import okhttp3.ResponseBody

class LocalDataStore : DataStore {

    override fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
       throw NotImplementedException("login")
    }
}