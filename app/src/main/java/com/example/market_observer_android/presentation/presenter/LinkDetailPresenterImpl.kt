package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView

class LinkDetailPresenterImpl(private val repository: Repository) : LinkDetailPresenter,
    BasePresenterImpl<LinkDetailView>() {

    override fun deleteLink(url: String) {
        PreferenceManager.setLinksRemainingCount(PreferenceManager.getLinksRemainingCount() + 1)
        repository.deleteLink(url)
        view?.onDeleteLink()
    }

    override fun saveResult(result: LinkResult) {
        repository.addSavedResult(result)
    }

    override fun unsaveResult(result: LinkResult) {
        repository.deleteSavedResults(result)
    }

    override fun getResults(url: String) {
        repository.getResultsWithSaved(url)
            .subscribe {
                view?.setResults(it!!)
            }
    }
}