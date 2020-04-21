package com.example.market_observer_android.presentation.mvp_view

import com.example.market_observer_android.domain.model.LinkResult

interface SavedResultsView {

    fun setSavedResults(results: List<LinkResult>)
}