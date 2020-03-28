package com.example.market_observer_android.presentation.injection

import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.presenter.*
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule::class]) //todo: not include DataModule
class PresentationModule {

    @Provides
    fun provideLoginPresenter(repository: Repository): LoginPresenter {
        return LoginPresenterImpl(repository)
    }

    @Provides
    fun provideHomePresenter(repository: Repository): HomePresenter {
        return HomePresenterImpl(repository)
    }

    @Provides
    fun provideAddLinkPresenter(repository: Repository): AddLinkPresenter {
        return AddLinkPresenterImpl(repository)
    }

    @Provides
    fun provideLinkDetailPresenter(repository: Repository): LinkDetailPresenter {
        return LinkDetailPresenterImpl(repository)
    }

    @Provides
    fun provideMyAccountPresenter(): MyAccountPresenter {
        return MyAccountPresenterImpl()
    }
}