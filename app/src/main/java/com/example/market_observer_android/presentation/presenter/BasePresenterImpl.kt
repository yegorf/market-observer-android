package com.example.market_observer_android.presentation.presenter

open class BasePresenterImpl<T> : BasePresenter<T> {

    protected var view: T? = null

    override fun onCreate(view: T) {
        this.view = view
    }
}