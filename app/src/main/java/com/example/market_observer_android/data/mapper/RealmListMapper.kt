package com.example.market_observer_android.data.mapper

import io.realm.RealmList
import io.realm.RealmResults

object RealmListMapper {

    fun <T> mapRealmListToList(results: RealmResults<T>): List<T> {
        val list = mutableListOf<T>()
        list.addAll(results)
        return list
    }

    fun <T> mapListToRealmList(list: List<T>): RealmList<T> {
        val realm = RealmList<T>()
        realm.addAll(list)
        return realm
    }
}