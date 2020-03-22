package com.example.market_observer_android.data.rest

import com.example.market_observer_android.data.entity.CredentialsEntity
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApi(val retrofitApi: RetrofitApi) {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody> {
        return retrofitApi.login(credentialsEntity)
    }
}