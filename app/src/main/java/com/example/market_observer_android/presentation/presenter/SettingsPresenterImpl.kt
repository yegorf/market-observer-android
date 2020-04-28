package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.mvp_view.SettingsView
import com.google.firebase.auth.FirebaseAuth

class SettingsPresenterImpl(private val repository: Repository) : SettingsPresenter,
    BasePresenterImpl<SettingsView>() {

    override fun onCreate(view: SettingsView) {
        super.onCreate(view)
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            view.setUserData(email)
        }
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
        view?.openLoginScreen()
    }

    override fun saveSettings(settings: SettingsEntity) {
        repository.saveSettings(settings)
    }
}