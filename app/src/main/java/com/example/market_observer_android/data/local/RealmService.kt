package com.example.market_observer_android.data.local

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.data.local.realm_entity.SavedResultRealm
import com.example.market_observer_android.data.util.LinkStructure
import com.example.market_observer_android.data.util.SavedResultStructure
import com.example.market_observer_android.domain.model.LinkResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults

class RealmService(private var realm: Realm) {

    private val tag = RealmService::class.java.simpleName

    fun addLink(linkRealm: LinkRealm) {
        realm.executeTransaction {
            linkRealm.userUid = FirebaseAuth.getInstance().currentUser?.uid
            it.insertOrUpdate(linkRealm)
        }
    }

    fun getAllLinks(): Observable<RealmResults<LinkRealm>> {
        return realm.where(LinkRealm::class.java)
            .equalTo(LinkStructure.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
            .findAll()
            .asFlowable()
            .toObservable()
    }

    fun deleteLink(url: String) {
        realm.executeTransaction {
            it.where(LinkRealm::class.java)
                .equalTo(LinkStructure.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(LinkStructure.URL, url)
                .findAll()
                .deleteAllFromRealm()
        }
    }

    fun addResults(url: String, results: List<LinkResultRealm>) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val link = it.where(LinkRealm::class.java)
                .equalTo(LinkStructure.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(LinkStructure.URL, url)
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
                .equalTo(LinkStructure.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(LinkStructure.URL, url)
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

    fun deleteSavedResult(result: LinkResult) {
        realm.executeTransaction {
            it.where(SavedResultRealm::class.java)
                .equalTo(SavedResultStructure.USER_UID, FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo(SavedResultStructure.URL, result.url)
                .findAll()
                .deleteAllFromRealm()
        }
    }

    fun getSavedResults(): Observable<RealmResults<SavedResultRealm>> {
        val user = FirebaseAuth.getInstance().currentUser?.uid
        var results: Observable<RealmResults<SavedResultRealm>> = Observable.empty()
        realm.executeTransaction {
            results = it.where(SavedResultRealm::class.java)
                .equalTo(LinkStructure.USER_UID, user)
                .findAll()
                .asFlowable().toObservable()
        }
        return results
    }
}