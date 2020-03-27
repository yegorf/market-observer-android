package com.example.market_observer_android.data.local

import com.example.market_observer_android.data.local.realm_entity.LinkRealm
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class RealmService {

    val realm: Realm = Realm.getDefaultInstance()

    fun addLink(url: String?, name: String?, periodicity: Int) {
        realm.beginTransaction()

        val link = LinkRealm()
        link.id = UUID.randomUUID().toString()
        link.name = name
        link.periodicity = periodicity
        link.url = url

        realm.insert(link)
        realm.commitTransaction()
    }

    fun getAllLinks(): Observable<RealmResults<LinkRealm>> {
        return realm.where(LinkRealm::class.java)
            .findAll()
            .asFlowable()
            .toObservable()
    }
}