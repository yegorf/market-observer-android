package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView

class LinkDetailPresenterImpl(private val repository: Repository) : LinkDetailPresenter,
    BasePresenterImpl<LinkDetailView>() {

    override fun deleteLink(url: String) {
        repository.deleteLink(url)
        view?.onDeleteLink()
    }
}