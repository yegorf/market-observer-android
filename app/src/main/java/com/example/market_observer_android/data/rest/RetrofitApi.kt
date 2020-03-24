package com.example.market_observer_android.data.rest

import com.example.market_observer_android.data.entity.CredentialsEntity
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface RetrofitApi {

    @POST("user/login")
    fun login(@Body credentialsEntity: CredentialsEntity): Observable<ResponseBody>
}