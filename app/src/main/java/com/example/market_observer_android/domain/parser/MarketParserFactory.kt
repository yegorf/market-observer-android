package com.example.market_observer_android.domain.parser

object MarketParserFactory {

    fun createParser(url: String): MarketParser? {
        return when (getServiceNameByUrl(url)) {
            MarketConfig.PLACE -> PlaceUAParser()
            MarketConfig.BESPLATKA -> BesplatkaParser()
            MarketConfig.RIA -> RiaParser()
            MarketConfig.PROM -> PromParser()
            MarketConfig.OLX -> OlxParser()
            MarketConfig.OLX_MOBILE -> OlxParser()
            else -> return null
        }
    }
}