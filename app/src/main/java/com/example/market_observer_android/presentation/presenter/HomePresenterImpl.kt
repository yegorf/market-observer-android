package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.common.util.UseCaseObserver
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.usecase.GetActiveLinksUseCase
import com.example.market_observer_android.presentation.mvp_view.HomeView

class HomePresenterImpl(
    private val repository: Repository,
    private val getActiveLinksUseCase: GetActiveLinksUseCase
) : HomePresenter,
    BasePresenterImpl<HomeView>() {

    override fun getActiveLinks() {
        getActiveLinksUseCase.execute(getActiveLinksObserver())
    }

    override fun updateLink(link: Link) {
        repository.addLink(link)
        if (link.isActive) {
            RxBus.sendData(Event.ADD_LINK_TO_OBSERVE, link)
        } else {
            RxBus.sendData(Event.REMOVE_LINK_FROM_OBSERVE, link.url!!)
        }
    }

    private fun getActiveLinksObserver(): UseCaseObserver<List<Link>> {
        return object : UseCaseObserver<List<Link>>() {
            override fun onNext(t: List<Link>) {
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