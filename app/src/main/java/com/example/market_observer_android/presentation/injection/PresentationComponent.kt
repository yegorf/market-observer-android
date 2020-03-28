package com.example.market_observer_android.presentation.injection

import com.example.market_observer_android.presentation.activity.LoginActivity
import com.example.market_observer_android.presentation.fragment.AddLinkFragment
import com.example.market_observer_android.presentation.fragment.HomeFragment
import com.example.market_observer_android.presentation.fragment.LinkDetailFragment
import com.example.market_observer_android.presentation.fragment.MyAccountFragment
import dagger.Component

@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(addLinkFragment: AddLinkFragment)

    fun inject(linkDetailFragment: LinkDetailFragment)

    fun inject(myAccountFragment: MyAccountFragment)
}