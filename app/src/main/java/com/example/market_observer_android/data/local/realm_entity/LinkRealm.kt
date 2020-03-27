package com.example.market_observer_android.data.local.realm_entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class LinkRealm(

    @PrimaryKey
    var id: String? = null,

    @Required
    var url: String? = null,

    var name: String? = null,

    var periodicity: Int = 0,

    var results: RealmList<LinkResultRealm>? = null

) : RealmObject()