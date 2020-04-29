package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult

interface MarketParser {

    fun parseTitle(url: String): String

    fun parseUrl(url: String): List<LinkResult>
}