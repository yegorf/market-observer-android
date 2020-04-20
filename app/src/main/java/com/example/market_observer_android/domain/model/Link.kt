package com.example.market_observer_android.domain.model

import java.io.Serializable

data class Link(
    var url: String? = null,
    var name: String? = null,
    var periodicity: Int = 0,
    var isActive: Boolean = true,
    var results: List<LinkResult> = mutableListOf()
) : Serializable