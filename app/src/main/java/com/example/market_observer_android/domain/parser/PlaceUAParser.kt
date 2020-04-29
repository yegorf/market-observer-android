package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class PlaceUAParser : MarketParser {

    override fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("input#j-f-query")
            .attr("value")
    }

    override fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("div.messages-list")[0]
            .select("div.msg-one")

        val results = mutableListOf<LinkResult>()

        return results
    }
}