package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable
import okhttp3.ResponseBody

interface Repository {

    fun login(credentialsEntity: CredentialsEntity): Observable<ResponseBody>

    fun getActiveLinks(): Observable<List<ActiveLink>>

    fun addLink(link: Link)

    fun deleteLink(url: String)

    fun addResults(url: String, results: List<LinkResult>)
}