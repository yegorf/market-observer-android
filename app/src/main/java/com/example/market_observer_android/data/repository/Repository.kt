package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.domain.model.ActiveLink
import okhttp3.ResponseBody
import rx.Observable

interface Repository {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody>

    fun getActiveLinks(): Observable<List<ActiveLink>>
}