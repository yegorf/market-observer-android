package com.example.market_observer_android.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class FirebaseService {

    private val tag = FirebaseService::class.java.simpleName

    fun signUp(email: String, password: String): Observable<Boolean> {
        val observable = PublishSubject.create<Boolean>()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    observable.onComplete()
                } else {
                    observable.onError(Exception(it.exception))
                }
            }
        return observable
    }

    fun signIn(email: String, password: String): Observable<Boolean> {
        val observable = PublishSubject.create<Boolean>()
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(tag, "$email success signIn")
                    observable.onComplete()
                } else {
                    Log.d(tag, "$email fail signIn")
                    observable.onError(Exception(it.exception))
                }
            }
        return observable
    }
}