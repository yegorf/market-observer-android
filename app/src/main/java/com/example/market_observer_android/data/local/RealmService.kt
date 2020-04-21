package com.example.market_observer_android.data.local

import android.util.Log
import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.data.local.realm_entity.SavedResultRealm
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults

class RealmService {

    private val tag = RealmService::class.java.simpleName
    var realm: Realm = Realm.getDefaultInstance()

    fun addLink(linkRealm: LinkRealm) {
        realm.executeTransaction {
            linkRealm.userUid = FirebaseAuth.getInstance().currentUser?.uid
            it.insertOrUpdate(linkRealm)
        }
    }

    fun getAllLinks(): Observable<RealmResults<LinkRealm>> {
        return realm.where(LinkRealm::class.java)
            .equalTo(LinkRealm.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
            .findAll()
            .asFlowable()
            .toObservable()
    }

    fun deleteLink(url: String) {
        realm.executeTransaction {
            it.where(LinkRealm::class.java)
                .equalTo(LinkRealm.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(LinkRealm.URL, url)
                .findAll()
                .deleteAllFromRealm()
        }
    }

    fun addResults(url: String, results: List<LinkResultRealm>) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val link = it.where(LinkRealm::class.java)
                .equalTo(LinkRealm.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(LinkRealm.URL, url)
                .findFirst()

            if (link != null) {
                link.results?.addAll(results)
                it.insertOrUpdate(link)
            }
        }
    }

    fun getResults(url: String): Observable<List<LinkResultRealm>?> {
        var result: Observable<List<LinkResultRealm>?> = Observable.empty()
        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val link = realm.where(LinkRealm::class.java)
                .equalTo(LinkRealm.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(LinkRealm.URL, url)
                .findFirst()

            if (link != null) {
                result = Observable.just(link.results)
            }
        }
        return result
    }

    fun addSavedResult(result: SavedResultRealm) {
        result.userUid = FirebaseAuth.getInstance().currentUser?.uid
        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insertOrUpdate(result)
        }
    }

    fun getSavedResults(): Observable<RealmResults<SavedResultRealm>> {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        var results: Observable<RealmResults<SavedResultRealm>> = Observable.empty()
        realm.executeTransaction {
            val findAll = it.where(SavedResultRealm::class.java)
                .equalTo(LinkRealm.USER_UID, user)
                .findAll()

            findAll.forEach { res ->
                Log.d(tag, res.title!!)
            }

            results = findAll.asFlowable().toObservable()
        }
        return results
    }
}