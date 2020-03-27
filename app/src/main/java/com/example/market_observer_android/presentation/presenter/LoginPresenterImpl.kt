package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.mvp_view.LoginView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class LoginPresenterImpl(val repository: Repository) : LoginPresenter,
    BasePresenterImpl<LoginView>() {

    override fun register(credentialsEntity: CredentialsEntity) {

    }

    override fun login(credentialsEntity: CredentialsEntity) {

    }
}