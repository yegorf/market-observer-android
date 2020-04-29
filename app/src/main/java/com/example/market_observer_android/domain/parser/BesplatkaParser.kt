package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class BesplatkaParser: MarketParser {

    override fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("input#search-input")
            .attr("value")
    }

    override fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("table#offers_table")[0]
            .select("tr.wrap")

        val results = mutableListOf<LinkResult>()

        return results
    }
}