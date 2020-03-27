package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.mvp_view.HomeView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HomePresenterImpl(val repository: Repository) : HomePresenter,
    BasePresenterImpl<HomeView>() {

    override fun getActiveLinks() {
        repository.getActiveLinks().subscribe(getObserver())
    }

    private fun getObserver(): Observer<List<ActiveLink>> {
        return object : Observer<List<ActiveLink>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: List<ActiveLink>) {
                view?.setActiveLinks(t)
            }

            override fun onError(e: Throwable) {
            }
        }
    }
}