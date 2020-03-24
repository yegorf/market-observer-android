package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.presentation.view.HomeView

interface HomePresenter {

    fun onCreate(view: HomeView)

    fun getActiveLinks()
}