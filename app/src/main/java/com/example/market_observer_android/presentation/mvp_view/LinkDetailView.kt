package com.example.market_observer_android.presentation.mvp_view

import com.example.market_observer_android.domain.model.LinkResult

interface LinkDetailView {

    fun onDeleteLink()

    fun setResults(results: List<LinkResult>)
}