package com.example.market_observer_android.data.datastore

import com.example.market_observer_android.data.firebase.FirebaseService
import io.reactivex.Observable

class RemoteDataStore(private val firebaseService: FirebaseService) {

    fun signUp(email: String, password: String): Observable<Boolean> {
        return firebaseService.signUp(email, password)
    }

    fun signIn(email: String, password: String): Observable<Boolean> {
        return firebaseService.signIn(email, password)
    }
}