package com.example.market_observer_android.data.firebase

import android.util.Log
import com.example.market_observer_android.data.entity.LinkEntity
import com.example.market_observer_android.data.entity.ResultEntity
import com.example.market_observer_android.data.entity.SettingsEntity
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

    fun addSavedResult(result: ResultEntity) {
        result.userUid = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(ResultEntity.TABLE_NAME)
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
            .collection(ResultEntity.TABLE_NAME)
            .whereEqualTo(ResultEntity.USER_UID, user)
            .get()
            .addOnSuccessListener { result ->
                val results = result.map {
                    it.toObject(LinkResult::class.java)
                }
                results.forEach {
                    it.isSaved = true
                }
                observable.onNext(results)
            }
            .addOnFailureListener { exception ->
                observable.onError(exception)
            }

        return observable
    }

    fun deleteSavedResult(result: LinkResult) {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(ResultEntity.TABLE_NAME)
            .whereEqualTo(ResultEntity.USER_UID, user)
            .whereEqualTo(ResultEntity.URL, result.url)
            .get()
            .addOnSuccessListener { res ->
                res.documents.forEach {
                    Firebase.firestore.collection(ResultEntity.TABLE_NAME)
                        .document(it.id)
                        .delete()
                }
            }
    }

    fun saveSettings(settings: SettingsEntity) {
        Firebase.firestore
            .collection(SettingsEntity.NAME)
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .set(settings)
    }

    fun getSettings(): Observable<SettingsEntity> {
        val observable = PublishSubject.create<SettingsEntity>()
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(SettingsEntity.NAME)
            .whereEqualTo(SettingsEntity.USER_UID, user)
            .get()
            .addOnSuccessListener { result ->
                val map = result.map {
                    it.toObject(SettingsEntity::class.java)
                }
                if (map.isNotEmpty()) {
                    observable.onNext(map[0])
                } else {
                    observable.onNext(SettingsEntity())
                }
            }
            .addOnFailureListener {
                observable.onError(it)
            }
        return observable
    }

    fun addLink(link: LinkEntity) {
        link.userUid = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(LinkEntity.TABLE_NAME)
            .add(link)
            .addOnSuccessListener {
                Log.d(tag, "Added")
            }
            .addOnFailureListener {
                Log.d(tag, "Cant add: ${it.message} -> ${it.cause}")
            }
    }

    fun getAllLinks(): Observable<List<LinkEntity>> {
        val observable = PublishSubject.create<List<LinkEntity>>()
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(LinkEntity.TABLE_NAME)
            .whereEqualTo(LinkEntity.USER_UID, user)
            .get()
            .addOnSuccessListener { result ->
                val results = result.map {
                    it.toObject(LinkEntity::class.java)
                }
                observable.onNext(results)
            }
            .addOnFailureListener { exception ->
                observable.onError(exception)
            }

        return observable
    }

    fun deleteLink(url: String) {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(LinkEntity.TABLE_NAME)
            .whereEqualTo(LinkEntity.USER_UID, user)
            .whereEqualTo(LinkEntity.URL, url)
            .get()
            .addOnSuccessListener { res ->
                res.documents.forEach {
                    Firebase.firestore.collection(LinkEntity.TABLE_NAME)
                        .document(it.id)
                        .delete()
                }
            }
    }

    fun addResults(url: String, newResults: List<LinkResult>) {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(LinkEntity.TABLE_NAME)
            .whereEqualTo(LinkEntity.USER_UID, user)
            .whereEqualTo(LinkEntity.URL, url)
            .get()
            .addOnSuccessListener {
                val link = it.first().toObject(LinkEntity::class.java)
                val results = mutableListOf<LinkResult>()
                results.addAll(link.results)
                results.addAll(newResults)
                Firebase.firestore
                    .collection(LinkEntity.TABLE_NAME)
                    .document(it.first().id)
                    .update(
                        mapOf(LinkEntity.RESULTS to results)
                    )
            }
    }
}