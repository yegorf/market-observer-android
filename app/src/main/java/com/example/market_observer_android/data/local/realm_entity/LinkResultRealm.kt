package com.example.market_observer_android.data.local.realm_entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LinkResultRealm(

    @PrimaryKey
    var id: String? = null,

    var url: String? = null,

    var title: String? = null,

    var imageUrl: String? = null,

    var time: String? = null,

    var location: String? = null,

    var price: String? = null,

    var link: LinkRealm? = null,

    var isSaved: Boolean = false

) : RealmObject()