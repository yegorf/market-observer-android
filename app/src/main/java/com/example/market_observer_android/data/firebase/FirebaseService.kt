package com.example.market_observer_android.data.firebase

import android.util.Log
import com.example.market_observer_android.data.util.SavedResultStructure
import com.example.market_observer_android.domain.model.LinkResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
                    observable.onComplete()
                } else {
                    observable.onError(Exception(it.exception))
                }
            }
        return observable
    }

    fun addSavedResult(result: HashMap<String, String?>) {
        result[SavedResultStructure.USER_UID] = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection("saved")
            .add(result)
            .addOnSuccessListener {
                Log.d(tag, "Added")
            }
            .addOnFailureListener {
                Log.d(tag, "Cant add: ${it.message} -> ${it.cause}")
            }
    }

    fun getSavedResults(): Observable<List<LinkResult>> {
        val observable = PublishSubject.create<List<LinkResult>>()

        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection("saved")
            .whereEqualTo(SavedResultStructure.USER_UID, user)
            .get()
            .addOnSuccessListener { result ->
                val results = result.map {
                    it.toObject(LinkResult::class.java)
                }
                observable.onNext(results)
            }
            .addOnFailureListener { exception ->
                observable.onError(exception)
            }

        return observable
    }
}