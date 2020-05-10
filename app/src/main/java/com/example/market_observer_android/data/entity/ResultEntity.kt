package com.example.market_observer_android.data.entity

data class ResultEntity(
    var userUid: String? = null,
    var url: String? = null,
    var title: String? = null,
    var imageUrl: String? = null,
    var time: String? = null,
    var location: String? = null,
    var price: String? = null,
    var link: String? = null
) {
    companion object {
        const val TABLE_NAME = "saved"
        const val USER_UID = "userUid"
        const val URL = "url"
        const val TITLE = "title"
        const val IMAGE_URL = "imageUrl"
        const val TIME = "time"
        const val LOCATION = "location"
        const val PRICE = "price"
        const val LINK = "link"
    }
}