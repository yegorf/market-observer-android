package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.presentation.mvp_view.AddLinkView

interface AddLinkPresenter : BasePresenter<AddLinkView> {

    fun addLink(url: String, name: String, periodicity: Int)

    fun editLink(url: String, name: String, periodicity: Int)
}