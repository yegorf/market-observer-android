package com.example.market_observer_android.domain.model

import java.io.Serializable

data class LinkResult(
    var url: String? = null,
    var title: String? = null,
    var imageUrl: String? = null,
    var time: String? = null,
    var location: String? = null,
    var price: String? = null
) : Serializable {
    override fun toString(): String {
        return """LinkResult:
            $title\n
            $url\n
        """.trimMargin()
    }
}