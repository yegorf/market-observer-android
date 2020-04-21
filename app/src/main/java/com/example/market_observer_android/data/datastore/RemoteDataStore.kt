package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.firebase.FirebaseService
import com.example.market_observer_android.data.mapper.MapperFactory
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable

class RemoteDataStore(
    private val firebaseService: FirebaseService, private val mapper: MapperFactory
) : DataStore {

    fun signUp(email: String, password: String): Observable<Boolean> {
        return firebaseService.signUp(email, password)
    }

    fun signIn(email: String, password: String): Observable<Boolean> {
        return firebaseService.signIn(email, password)
    }

    override fun addSavedResult(result: LinkResult) {
        firebaseService.addSavedResult(mapper.resultToMapMapper().transform(result))
    }

    override fun getSavedResults(): Observable<List<LinkResult>> {
        return firebaseService.getSavedResults()
    }
}