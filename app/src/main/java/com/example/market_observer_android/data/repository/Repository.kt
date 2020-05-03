package com.example.market_observer_android.data.repository

import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable

interface Repository {

    fun signUp(email: String, password: String): Observable<Boolean>

    fun signIn(email: String, password: String): Observable<Boolean>

    fun getActiveLinks(): Observable<List<Link>>

    fun addLink(link: Link)

    fun deleteLink(url: String)

    fun addResults(url: String, results: List<LinkResult>)

    fun getResultsWithSaved(url: String): Observable<List<LinkResult>>

    fun getResults(url: String): Observable<List<LinkResult>>

    fun addSavedResult(result: LinkResult)

    fun getSavedResults(): Observable<List<LinkResult>>

    fun deleteSavedResults(result: LinkResult)

    fun saveSettings(settings: SettingsEntity)

    fun getSettings(): Observable<SettingsEntity>
}