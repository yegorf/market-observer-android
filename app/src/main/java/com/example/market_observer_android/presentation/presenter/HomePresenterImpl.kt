package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.common.util.UseCaseObserver
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.usecase.GetActiveLinksUseCase
import com.example.market_observer_android.presentation.mvp_view.HomeView

class HomePresenterImpl(private val getActiveLinksUseCase: GetActiveLinksUseCase) : HomePresenter,
    BasePresenterImpl<HomeView>() {

    override fun getActiveLinks() {
        getActiveLinksUseCase.execute(getActiveLinksObserver())
    }

    private fun getActiveLinksObserver(): UseCaseObserver<List<ActiveLink>> {
        return object : UseCaseObserver<List<ActiveLink>>() {
            override fun onNext(t: List<ActiveLink>) {
                super.onNext(t)
                view?.setActiveLinks(t)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getActiveLinksUseCase.stop()
    }
}