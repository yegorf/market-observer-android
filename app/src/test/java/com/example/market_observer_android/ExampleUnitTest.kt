package com.example.market_observer_android

import com.example.market_observer_android.domain.parser.OlxParser
import com.example.market_observer_android.domain.parser.PlaceUAParser
import com.example.market_observer_android.domain.parser.PromParser
import com.example.market_observer_android.domain.parser.RiaParser
import io.opencensus.internal.StringUtils
import org.jsoup.helper.StringUtil
import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val url = "https://prom.ua/search?search_term=iphone+11"

        val title = PromParser().parseTitle(url)
        println(title)

        val list = PromParser().parseUrl(url)
        list.forEach {
            println(it.toString())
        }
    }
}
