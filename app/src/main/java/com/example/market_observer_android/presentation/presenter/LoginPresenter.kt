package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.entity.CredentialsEntity

interface LoginPresenter {

    fun register(credentialsEntity: CredentialsEntity)

    fun login(credentialsEntity: CredentialsEntity)
}