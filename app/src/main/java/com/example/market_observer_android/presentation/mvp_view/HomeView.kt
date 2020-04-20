package com.example.market_observer_android.presentation.mvp_view

import com.example.market_observer_android.domain.model.Link

interface HomeView {

    fun setActiveLinks(links: List<Link>?)
}