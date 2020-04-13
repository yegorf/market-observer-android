package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.mvp_view.AddLinkView

interface AddLinkPresenter : BasePresenter<AddLinkView> {

    fun addLink(link: Link)

    fun editLink(link: Link)
}