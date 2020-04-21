package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.mvp_view.SavedResultsView
import io.reactivex.disposables.CompositeDisposable

class SavedResultsPresenterImpl(private val repository: Repository) : SavedResultsPresenter,
    BasePresenterImpl<SavedResultsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getSavedResults() {
        val subscribe = repository.getSavedResults()
            .subscribe {
                view?.setSavedResults(it)
            }
        compositeDisposable.add(subscribe)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}