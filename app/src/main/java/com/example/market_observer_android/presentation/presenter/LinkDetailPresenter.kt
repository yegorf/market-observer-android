package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView

interface LinkDetailPresenter : BasePresenter<LinkDetailView> {

    fun deleteLink(url: String)

    fun saveResult(result: LinkResult)

    fun unsaveResult(result: LinkResult)

    fun getResults(url: String)
}