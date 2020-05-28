package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class PlaceUAParser : MarketParser {

    override fun getMarketName() = MarketConfig.PLACE

    override fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("input#j-f-query")
            .attr("value")
    }

    override fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("div.sr-page__list.sr-page__list_desktop.hidden-phone")[0]
            .select("div.sr-page__list__item")

        val results = mutableListOf<LinkResult>()
        rows.forEach {
            val result = LinkResult()
            result.title = it.select("div.sr-page__list__item_title").select("a").text()
            result.price = it.select("td.sr-page__list__item_price").select("strong").text()
            result.location = it.select("i.fa fa-map-marker").text()
            result.url = it.select("div.sr-page__list__item_title").select("a").attr("href")
            result.imageUrl = it.select("img.rel br2 zi3 shadow").attr("src")
            results.add(result)
        }
        return results
    }
}