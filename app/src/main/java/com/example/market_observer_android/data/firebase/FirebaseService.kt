package com.example.market_observer_android.data.firebase

import android.util.Log
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

    fun addSavedResult(result: LinkResult) {
        val resultMap = hashMapOf(
            "url" to result.url,
            "title" to result.title,
            "imageUrl" to result.imageUrl,
            "price" to result.price,
            "location" to result.location,
            "time" to result.time,
            "user_uid" to FirebaseAuth.getInstance().currentUser?.uid
        )

        Firebase.firestore
            .collection("saved")
            .add(resultMap)
            .addOnSuccessListener {
                Log.d(tag, "Added")
            }
            .addOnFailureListener {
                Log.d(tag, "Cant add: ${it.message} -> ${it.cause}")
            }
    }

    fun getSavedResults() {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection("saved")
            .whereEqualTo("user_uid", user)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(tag, "${document.id} -> ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(tag, "Error getting documents.", exception)
            }
    }
}