package com.example.market_observer_android.presentation.injection

import com.example.market_observer_android.presentation.activity.LoginActivity
import com.example.market_observer_android.presentation.fragment.*
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(addLinkFragment: AddLinkFragment)

    fun inject(linkDetailFragment: LinkDetailFragment)

    fun inject(settingsFragment: SettingsFragment)

    fun inject(savedResultsFragment: SavedResultsFragment)
}