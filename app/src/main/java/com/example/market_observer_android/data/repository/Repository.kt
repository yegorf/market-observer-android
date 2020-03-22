package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.entity.CredentialsEntity
import io.reactivex.Observable
import okhttp3.ResponseBody

interface Repository {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody>
}