package com.example.market_observer_android.common.injection

import com.example.market_observer_android.common.application.Application
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: Application)
}