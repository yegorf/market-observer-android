package com.example.market_observer_android

import com.example.market_observer_android.domain.parser.OlxParser
import com.example.market_observer_android.domain.parser.PlaceUAParser
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
        val list = PlaceUAParser().parseUrl("https://besplatka.ua/all/q-iphone+11")
        list.forEach {
            println(it.toString())
        }
    }
}
