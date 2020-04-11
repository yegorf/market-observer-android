package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.mvp_view.AddLinkView

class AddLinkPresenterImpl(val repository: Repository) : AddLinkPresenter,
    BasePresenterImpl<AddLinkView>() {

    private val bus = RxBus

    override fun addLink(url: String, name: String, periodicity: Int) {
        PreferenceManager.setLinksRemainingCount(PreferenceManager.getLinksRemainingCount() - 1)
        repository.addLink(Link(url, name, periodicity))
        bus.sendData(Event.ADD_LINK_TO_OBSERVE, Link(url, name, periodicity))
        view?.onSuccess()
    }

    override fun editLink(url: String, name: String, periodicity: Int) {
        repository.addLink(Link(url, name, periodicity))
        bus.sendData(Event.ADD_LINK_TO_OBSERVE, Link(url, name, periodicity))
        view?.onSuccess()
    }
}