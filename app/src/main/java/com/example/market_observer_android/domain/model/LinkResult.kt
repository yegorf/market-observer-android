package com.example.market_observer_android.domain.model

import java.io.Serializable

data class LinkResult(
    var url: String? = null,
    var title: String? = null,
    var imageUrl: String? = null,
    var time: String? = null,
    var location: String? = null,
    var price: String? = null,
    var isSaved: Boolean = false,
    var isViewed: Boolean = false
) : Serializable {

    override fun toString(): String {
        return "$title\n$url\n"
    }

    override fun equals(other: Any?): Boolean {
        if (url != null && other != null && other is LinkResult) {
            return url.equals(other.url)
        }
        return false
    }

    override fun hashCode(): Int {
        return url?.hashCode() ?: 0
    }
}