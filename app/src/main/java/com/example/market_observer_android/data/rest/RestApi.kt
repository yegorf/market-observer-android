package com.example.market_observer_android.data.rest

import com.example.market_observer_android.data.entity.CredentialsEntity
import okhttp3.ResponseBody
import rx.Observable

class RestApi(val retrofitApi: RetrofitApi) {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return retrofitApi.login(credentialsEntity)
    }
}