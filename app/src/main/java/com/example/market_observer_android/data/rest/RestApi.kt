package com.example.market_observer_android.data.rest

import com.example.market_observer_android.data.entity.CredentialsEntity
import io.reactivex.Observable
import okhttp3.ResponseBody

class RestApi(val retrofitApi: RetrofitApi) {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return retrofitApi.login(credentialsEntity)
    }
}