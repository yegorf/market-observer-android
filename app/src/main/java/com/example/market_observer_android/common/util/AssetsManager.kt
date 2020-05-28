package com.example.market_observer_android.common.util

import android.content.Context
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

object AssetsManager {

    fun getMarketsList(context: Context): List<Market> {
        val json = context.assets
            .open("markets.json")
            .bufferedReader()
            .use {
                it.readText()
            }

        val type = object : TypeToken<List<Market>>() {}.type
        return Gson().fromJson(json, type)
    }
}