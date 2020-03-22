package com.example.market_observer_android.presentation.injection

import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.presenter.LoginPresenter
import com.example.market_observer_android.presentation.presenter.LoginPresenterImpl
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class]) //todo: not include DataModule
class PresentationModule {

    @Provides
    fun provideLoginPresenter(repository: Repository): LoginPresenter {
        return LoginPresenterImpl(repository)
    }
}