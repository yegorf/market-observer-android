package com.example.market_observer_android.data.entity

import com.example.market_observer_android.domain.model.LinkResult

data class LinkEntity(
    var url: String? = null,
    var name: String? = null,
    var periodicity: Int = 0,
    var isActive: Boolean = true,
    var results: List<LinkResult> = mutableListOf()
) {
    companion object {
        const val USER_UID = "userUid"
        const val URL = "url"
        const val NAME = "name"
        const val PERIODICITY = "periodicity"
        const val IS_ACTIVE = "isActive"
        const val RESULTS = "results"
    }
}