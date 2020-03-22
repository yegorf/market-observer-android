package com.example.market_observer_android.common.injection

import com.example.market_observer_android.common.application.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): android.app.Application {
        return application
    }
}