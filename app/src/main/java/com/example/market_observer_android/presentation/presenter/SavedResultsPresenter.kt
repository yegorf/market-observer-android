package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.mvp_view.SavedResultsView

interface SavedResultsPresenter : BasePresenter<SavedResultsView> {

    fun getSavedResults()

    fun deleteSavedResult(result: LinkResult)
}