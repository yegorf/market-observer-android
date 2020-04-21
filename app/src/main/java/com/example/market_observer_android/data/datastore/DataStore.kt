package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable

interface DataStore {

    fun addSavedResult(result: LinkResult)

    fun getSavedResults(): Observable<List<LinkResult>>

    fun deleteSavedResults(result: LinkResult)
}