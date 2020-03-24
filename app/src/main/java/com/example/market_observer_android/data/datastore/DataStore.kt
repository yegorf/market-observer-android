package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.entity.CredentialsEntity
import okhttp3.ResponseBody
import rx.Observable

interface DataStore {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody>
}