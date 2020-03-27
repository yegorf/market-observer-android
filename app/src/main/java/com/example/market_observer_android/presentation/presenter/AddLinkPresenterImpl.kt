package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.mvp_view.AddLinkView

class AddLinkPresenterImpl(val repository: Repository) : AddLinkPresenter,
    BasePresenterImpl<AddLinkView>() {

    private val bus = RxBus

    override fun addLink(url: String, name: String, periodicity: Int) {
        //todo: 1)validation 2)add to db 3)send to api
        bus.sendData(Event.ADD_LINK_TO_OBSERVE, Link(url, name, periodicity))
    }
}