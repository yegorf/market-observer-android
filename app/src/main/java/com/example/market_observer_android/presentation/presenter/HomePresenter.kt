package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.presentation.mvp_view.HomeView

interface HomePresenter : BasePresenter<HomeView> {

    fun getActiveLinks()
}