package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.mvp_view.AddLinkView

class AddLinkPresenterImpl(val repository: Repository) : AddLinkPresenter,
    BasePresenterImpl<AddLinkView>() {

    private val bus = RxBus

    override fun addLink(link: Link) {
        repository.addLink(link)
        if (link.isActive) {
            bus.sendData(Event.ADD_LINK_TO_OBSERVE, link)
        }
        view?.onSuccess()
    }

    override fun editLink(link: Link) {
        repository.addLink(link)
        if (link.isActive) {
            bus.sendData(Event.ADD_LINK_TO_OBSERVE, link)
        }
        view?.onSuccess()
    }
}