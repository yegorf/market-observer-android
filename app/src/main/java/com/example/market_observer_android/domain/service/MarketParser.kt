package com.example.market_observer_android.domain.service

import android.util.Log
import com.example.market_observer_android.domain.model.LinkResult
import org.jsoup.Jsoup

class MarketParser {

    private val TAG = MarketParser::class.java.simpleName

    fun parseUrl(url: String): List<LinkResult> {
        Log.i(TAG, "parse: $url")
        try {
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

                result.title = select.text()
                result.url = select.attr("href")
                result.imageUrl = img.attr("src")
                result.location = location

                result.time = time
                results.add(result)
            }
            return results
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            return listOf()
        }
    }
}