package com.example.market_observer_android.domain.util

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class MarketParser {

    fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("input#search-text")
            .attr("value")
    }

    fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("table#offers_table")[0]
            .select("tr.wrap")

        val results = mutableListOf<LinkResult>()
        rows.forEach {
            val result = LinkResult()

            val select = it.select("a.marginright5")
            val img = it.select("img.fleft")
            val location = it.select("p.lheight16").select("span")[0].text()
            val time = it.select("p.lheight16").select("span")[1].text()
            val price = it.select("p.price").text()

            result.title = select.text()
            result.url = select.attr("href")
            result.imageUrl = img.attr("src")
            result.location = location
            result.price = price
            result.time = time

            results.add(result)
        }
        return results
    }
}