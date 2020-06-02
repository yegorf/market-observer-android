package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView
import io.reactivex.disposables.CompositeDisposable

class LinkDetailPresenterImpl(private val repository: Repository) : LinkDetailPresenter,
    BasePresenterImpl<LinkDetailView>() {

    private val disposables = CompositeDisposable()

    override fun deleteLink(url: String) {
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
        disposables.add(repository.getResultsWithSaved(url)
            .subscribe {
                view?.setResults(it!!)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}