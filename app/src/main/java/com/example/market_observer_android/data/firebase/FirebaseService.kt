package com.example.market_observer_android.data.firebase

import android.util.Log
import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.data.util.SavedResultStructure
import com.example.market_observer_android.data.util.SettingsStructure
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

    fun deleteSavedResult(result: LinkResult) {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection("saved")
            .whereEqualTo(SavedResultStructure.USER_UID, user)
            .whereEqualTo(SavedResultStructure.URL, result.url)
            .get()
            .addOnSuccessListener { res ->
                res.documents.forEach {
                    Firebase.firestore.collection("saved").document(it.id).delete()
                }
            }
    }

    fun saveSettings(settings: SettingsEntity) {
        val settingsMap = hashMapOf(
            SettingsStructure.USER_UID to FirebaseAuth.getInstance().currentUser?.uid,
            SettingsStructure.APP_NOTIFICATIONS to settings.appNotificationsOn,
            SettingsStructure.EMAIL_NOTIFICATIONS to settings.emailNotificationsOn,
            SettingsStructure.OBSERVE_NEW_LINK to settings.observeNewLinkOn,
            SettingsStructure.AUTO_BACKUP to settings.autoBackupOn
        )

        Firebase.firestore
            .collection(SettingsStructure.NAME)
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .set(settingsMap)
    }

    fun getSettings(): Observable<SettingsEntity> {
        val observable = PublishSubject.create<SettingsEntity>()
        val user = FirebaseAuth.getInstance().currentUser?.uid
        Firebase.firestore
            .collection(SettingsStructure.NAME)
            .whereEqualTo(SettingsStructure.USER_UID, user)
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
}