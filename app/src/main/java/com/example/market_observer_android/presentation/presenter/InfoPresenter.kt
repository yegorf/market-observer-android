package com.example.market_observer_android.presentation.presenter

import android.content.Context
import com.example.market_observer_android.presentation.mvp_view.InfoView

interface InfoPresenter : BasePresenter<InfoView> {

    fun contactUs(context: Context)
}