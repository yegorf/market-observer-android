package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class BesplatkaParser: MarketParser {

    override fun getMarketName() = "besplatka.ua"

    override fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("input#search-input")
            .attr("value")
    }

    override fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("div.messages-list")[0]
            .select("div.msg-one")

        val results = mutableListOf<LinkResult>()
        rows.forEach {
            val result = LinkResult()
            result.title = it.select("a.m-title").text()
            result.price = it.select("p.m-price").text()
            result.location = it.select("li.m-region").text()
            result.url = "https://besplatka.ua/" + it.select("a.w-image").attr("href")
            result.imageUrl = it.select("img.img-responsive lazy loaded").attr("data-src")
            results.add(result)
        }
        return results
    }
}