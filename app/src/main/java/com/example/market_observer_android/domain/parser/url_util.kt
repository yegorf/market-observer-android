package com.example.market_observer_android.domain.parser

import java.util.regex.Pattern

fun getServiceNameByUrl(url: String): String? {
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