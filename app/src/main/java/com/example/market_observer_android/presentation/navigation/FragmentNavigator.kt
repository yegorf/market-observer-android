package com.example.market_observer_android.presentation.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.fragment.AddLinkFragment
import com.example.market_observer_android.presentation.fragment.HomeFragment
import com.example.market_observer_android.presentation.fragment.LinkDetailFragment

class FragmentNavigator(private val fragmentManager: androidx.fragment.app.FragmentManager) {

    companion object {
        const val SCREEN_HOME = "SCREEN_HOME"
        const val SCREEN_ADD_LINK = "SCREEN_ADD_LINK"
        const val SCREEN_LINK_RESULTS = "SCREEN_LINK_RESULTS"
        var currentScreen = SCREEN_HOME
    }

    fun openHome() {
        val fragment = getFragmentForScreen(SCREEN_HOME)
        if (fragment != null) {
            open(fragment, SCREEN_HOME)
            currentScreen = SCREEN_HOME
        }
    }

    fun openLinkDetails(link: ActiveLink) {
        val fragment = LinkDetailFragment.newInstance(link)
        open(fragment, SCREEN_LINK_RESULTS)
        currentScreen = SCREEN_LINK_RESULTS
    }

    fun openEditLink(link: Link) {
        val fragment = AddLinkFragment.newInstance(link)
        open(fragment, SCREEN_ADD_LINK)
        currentScreen = SCREEN_ADD_LINK
    }

    fun openFragment(screenName: String) {
        if (currentScreen != screenName) {
            val fragment = getFragmentForScreen(screenName)
            if (fragment != null) {
                open(fragment, screenName)
                currentScreen = screenName
            }
        }
    }

    fun navigateBack(activity: Activity) {
        if (currentScreen == SCREEN_HOME) {
            activity.finish()
        } else {
            openFragment(SCREEN_HOME)
        }
    }

    private fun open(fragment: Fragment, screenName: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(screenName)
            .commit()
    }

    private fun getFragmentForScreen(screenName: String): Fragment? {
        return when (screenName) {
            SCREEN_HOME -> HomeFragment.newInstance()
            SCREEN_ADD_LINK -> AddLinkFragment.newInstance()
            else -> null
        }
    }
}