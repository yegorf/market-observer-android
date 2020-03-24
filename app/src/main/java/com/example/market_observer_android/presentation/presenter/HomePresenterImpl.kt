package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.view.HomeView
import rx.Observer

class HomePresenterImpl(val repository: Repository) : HomePresenter {

    lateinit var view: HomeView

    override fun onCreate(view: HomeView) {
        this.view = view
    }

    override fun getActiveLinks() {
        repository.getActiveLinks().subscribe(getObserver())
    }

    private fun getObserver(): Observer<List<ActiveLink>> {
        return object : Observer<List<ActiveLink>> {
            override fun onError(e: Throwable?) {

            }

            override fun onNext(t: List<ActiveLink>?) {
                view.setActiveLinks(t)
            }

            override fun onCompleted() {

            }

        }
    }
}