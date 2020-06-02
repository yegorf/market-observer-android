package com.example.market_observer_android

import com.example.market_observer_android.domain.parser.MarketParserFactory
import com.example.market_observer_android.domain.parser.OlxParser
import com.example.market_observer_android.domain.parser.PromParser
import org.junit.Test


class ParserFactoryTest {

    @Test
    fun promParserTest() {
        val url = "https://prom.ua/search?search_term=iphone+11"
        val parser = MarketParserFactory.createParser(url)
        assert(parser is PromParser)
    }

    @Test
    fun olxParserTest() {
        val url = "https://www.olx.ua/list/q-iphone-11/"
        val parser = MarketParserFactory.createParser(url)
        assert(parser is OlxParser)
    }
}
