package com.example.market_observer_android.presentation.injection

import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.usecase.GetActiveLinksUseCase
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
    fun provideHomePresenter(repository: Repository, getActiveLinksUseCase: GetActiveLinksUseCase): HomePresenter {
        return HomePresenterImpl(repository, getActiveLinksUseCase)
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
    fun provideSettingsPresenter(): SettingsPresenter {
        return SettingsPresenterImpl()
    }

    @Provides
    fun provideSavedResultsPresenter(repository: Repository): SavedResultsPresenter {
        return SavedResultsPresenterImpl(repository)
    }
}