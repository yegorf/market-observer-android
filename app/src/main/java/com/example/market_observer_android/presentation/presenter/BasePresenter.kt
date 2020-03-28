package com.example.market_observer_android.presentation.presenter

interface BasePresenter<T> {

    fun onCreate(view: T)

    fun onDestroy()
}