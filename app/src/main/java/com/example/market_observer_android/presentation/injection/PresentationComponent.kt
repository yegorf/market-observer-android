package com.example.market_observer_android.presentation.injection

import com.example.market_observer_android.presentation.activity.LoginActivity
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(loginActivity: LoginActivity)
}