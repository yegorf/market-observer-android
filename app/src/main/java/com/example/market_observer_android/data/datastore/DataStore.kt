package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.entity.CredentialsEntity
import io.reactivex.Observable
import okhttp3.ResponseBody

interface DataStore {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody>
}