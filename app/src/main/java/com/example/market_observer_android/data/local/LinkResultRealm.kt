package com.example.market_observer_android.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LinkResultRealm(

    @PrimaryKey
    var id: Long = 0,

    var url: String? = null,

    var title: String? = null,

    var imageUrl: String? = null,

    var time: String? = null,

    var location: String? = null,

    var link: LinkRealm? = null

) : RealmObject()