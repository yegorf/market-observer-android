package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.presentation.mvp_view.SettingsView

interface SettingsPresenter : BasePresenter<SettingsView> {

    fun signOut()

    fun saveSettings(settings: SettingsEntity)
}