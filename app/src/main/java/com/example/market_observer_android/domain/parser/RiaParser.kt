package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class RiaParser : MarketParser {

    override fun getMarketName() = MarketConfig.RIA

    override fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("span.item-selected-name")
            .text()
    }

    override fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("div.clearfix.search-items")[1]
            .select("div.ticket-clean")

        val results = mutableListOf<LinkResult>()
        rows.forEach {
            val result = LinkResult()
            result.title = it.select("a.ticket-title").text()
            result.price = it.select("strong.price.size20").text()
            result.location = it.select("div.location.grey").text()
            result.url = it.select("a.ticket-title").attr("href")
            result.imageUrl = it.select("a.photo-185x120.loaded").attr("href")
            results.add(result)
        }
        return results
    }
}