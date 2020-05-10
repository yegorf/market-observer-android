package com.example.market_observer_android.data.mapper

interface Mapper<in A, out B> {

    fun transform(source: A): B
}