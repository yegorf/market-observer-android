package com.example.market_observer_android.data.local.realm_entity

import com.example.market_observer_android.domain.model.LinkResult
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class SavedResultRealm(
    @PrimaryKey
    var url: String? = null,

    var title: String? = null,

    var imageUrl: String? = null,

    var time: String? = null,

    var location: String? = null,

    var price: String? = null,

    var userUid: String? = null
) : RealmObject() {

    fun toLinkResult(): LinkResult {
        val result = LinkResult()
        result.url = url
        result.title = title
        result.imageUrl = imageUrl
        result.time = time
        result.location = location
        result.price = price
        return result
    }

    companion object {
        fun fromLinkResult(result: LinkResult): SavedResultRealm {
            val realm = SavedResultRealm()
            realm.url = result.url
            realm.title = result.title
            realm.imageUrl = result.imageUrl
            realm.time = result.time
            realm.location = result.location
            realm.price = result.price
            return realm
        }
    }
}