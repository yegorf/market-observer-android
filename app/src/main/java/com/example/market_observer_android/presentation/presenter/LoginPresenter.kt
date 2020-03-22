package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.entity.CredentialsEntity

interface LoginPresenter {

    fun login(credentialsEntity: CredentialsEntity)
}