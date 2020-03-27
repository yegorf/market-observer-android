package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import io.reactivex.Observable
import okhttp3.ResponseBody

interface Repository {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody>

    fun getActiveLinks(): Observable<List<ActiveLink>>

    fun addLink(link: Link)
}