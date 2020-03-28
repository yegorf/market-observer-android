package com.example.market_observer_android.data.local

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import com.example.market_observer_android.data.local.realm_entity.LinkResultRealm
import com.example.market_observer_android.domain.model.LinkResult
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class RealmService {

    var realm: Realm = Realm.getDefaultInstance()

    fun addLink(url: String?, name: String?, periodicity: Int) {
        realm.executeTransaction {
            val link = LinkRealm()
            link.id = UUID.randomUUID().toString()
            link.name = name
            link.periodicity = periodicity
            link.url = url
            it.insert(link)
        }
    }

    fun getAllLinks(): Observable<RealmResults<LinkRealm>> {
        return realm.where(LinkRealm::class.java)
            .findAll()
            .asFlowable()
            .toObservable()
    }

    fun deleteLink(url: String) {
        realm.executeTransaction {
            it.where(LinkRealm::class.java)
                .equalTo("url", url)
                .findAll()
                .deleteAllFromRealm()
        }
    }

    fun addResults(url: String, results: List<LinkResultRealm>) {
        realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val link = it.where(LinkRealm::class.java)
                .equalTo("url", url)
                .findFirst()

            if (link != null) {
                link.results?.addAll(results)
                it.insertOrUpdate(link)
            }
        }
    }
}