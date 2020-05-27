package com.example.market_observer_android.domain.parser

import java.util.regex.Pattern

object MarketParserFactory {

    fun createParser(url: String): MarketParser? {
        return when (getName(url)) {
            MarketConfig.PLACE -> PlaceUAParser()
            MarketConfig.BESPLATKA -> BesplatkaParser()
            MarketConfig.RIA -> RiaParser()
            MarketConfig.PROM -> PromParser()
            MarketConfig.OLX -> OlxParser()
            MarketConfig.OLX_MOBILE -> OlxParser()
            else -> return null
        }
    }

    private fun getName(url: String): String? {
        val matcher = Pattern.compile("//(.*?)/").matcher(url)
        return if (matcher.find()) {
            val result = matcher.group(1)
            if (result != null && result.startsWith("www")) {
                result.substring(4)
            } else {
                result
            }
        } else {
            null
        }
    }
}