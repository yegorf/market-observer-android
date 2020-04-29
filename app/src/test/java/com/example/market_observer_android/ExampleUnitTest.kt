package com.example.market_observer_android

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
        val url = "https://www.olx.ua/list/q-iphone-11/"
        val pattern = Pattern.compile("//(.*?)/")
        val matcher = pattern.matcher(url)
        if (matcher.find()) {
            println(matcher.group(1))
        } else {
            println("Not found")
        }
    }
}
