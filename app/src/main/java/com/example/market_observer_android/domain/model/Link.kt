package com.example.market_observer_android.domain.model

import java.io.Serializable

data class Link(
    var url: String,
    var name: String,
    var periodicity: Int
) : Serializable