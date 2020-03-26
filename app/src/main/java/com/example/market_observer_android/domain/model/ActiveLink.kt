package com.example.market_observer_android.domain.model

import java.io.Serializable

data class ActiveLink(
    var link: Link,
    var results: List<Result>
) : Serializable