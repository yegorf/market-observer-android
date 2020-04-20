package com.example.market_observer_android.data.local.realm_entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LinkRealm(

    @PrimaryKey
    var url: String? = null,

    var name: String? = null,

    var periodicity: Int = 0,

    var results: RealmList<LinkResultRealm>? = null,

    var isActive: Boolean = true,

    var userUid: String? = null

) : RealmObject() {
    companion object {
        const val URL = "url"
        const val USER_UID = "userUid"
    }
}