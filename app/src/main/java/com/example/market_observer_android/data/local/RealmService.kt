package com.example.market_observer_android.data.local

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults

class RealmService {

    var realm: Realm = Realm.getDefaultInstance()

    fun addLink(linkRealm: LinkRealm) {
        realm.executeTransaction {
            linkRealm.userUid = FirebaseAuth.getInstance().currentUser?.uid
            it.insertOrUpdate(linkRealm)
        }
    }

    fun getAllLinks(): Observable<RealmResults<LinkRealm>> {
        return realm.where(LinkRealm::class.java)
            .equalTo("userUid", FirebaseAuth.getInstance().currentUser?.uid)
            .findAll()
            .asFlowable()
            .toObservable()
    }

    fun deleteLink(url: String) {
        realm.executeTransaction {
            it.where(LinkRealm::class.java)
                .equalTo("userUid", FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo("url", url)
                .findAll()
                .deleteAllFromRealm()
        }
    }

    fun addResults(url: String, results: List<LinkResultRealm>) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val link = it.where(LinkRealm::class.java)
                .equalTo("userUid", FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo("url", url)
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
                .equalTo("userUid", FirebaseAuth.getInstance().currentUser?.uid)
                .equalTo("url", url)
                .findFirst()

            if (link != null) {
                result = Observable.just(link.results)
            }
        }
        return result
    }
}