package com.example.market_observer_android.presentation.presenter

import android.content.Context
import com.example.market_observer_android.domain.util.EmailSender
import com.example.market_observer_android.presentation.mvp_view.InfoView

class InfoPresenterImpl : InfoPresenter, BasePresenterImpl<InfoView>() {

    override fun contactUs(context: Context) {
        EmailSender.contactUs(context)
    }
}