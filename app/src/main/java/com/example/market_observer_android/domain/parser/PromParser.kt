package com.example.market_observer_android.domain.parser

import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class PromParser : MarketParser {

    override fun getMarketName() = MarketConfig.PROM

    override fun parseTitle(url: String): String {
        return Jsoup.connect(url)
            .get()
            .select("input.ps-search__field--3s5Te")
            .attr("value")
    }

    override fun parseUrl(url: String): List<LinkResult> {
        val document = Jsoup.connect(url).get()
        val rows = document.select("div.x-catalog-gallery__list")[0]
            .select("div.x-gallery-tile.js-gallery-tile.js-productad.x-gallery-tile_type_click")

        val results = mutableListOf<LinkResult>()
        rows.forEach {
            val result = LinkResult()
            result.title = it.select("span.ek-link.ek-link_style_multi-line").text()
            result.price = it.select("span.x-gallery-tile__price").attr("data-qaprice")
            result.location =
                it.select("div.ek-text.ek-text_color_bluey-grey").select("span").text()
            result.url = it.select("a.x-gallery-tile__tile-link").attr("href")
            result.imageUrl = it.select("img.x-gallery-tile__tile-link").attr("src")
            results.add(result)
        }
        return results
    }
}