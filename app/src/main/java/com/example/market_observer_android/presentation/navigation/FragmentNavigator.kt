package com.example.market_observer_android.presentation.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.fragment.*

class FragmentNavigator(private val fragmentManager: androidx.fragment.app.FragmentManager) {

    enum class Screen {
        HOME,
        ADD_LINK,
        LINK_RESULTS,
        MY_ACCOUNT,
        SETTINGS
    }

    companion object {
        var currentScreen = Screen.HOME
    }

    fun openHome() {
        val fragment = getFragmentForScreen(Screen.HOME)
        if (fragment != null) {
            open(fragment, Screen.HOME)
            currentScreen = Screen.HOME
        }
    }

    fun openLinkDetails(link: Link) {
        val fragment = LinkDetailFragment.newInstance(link)
        open(fragment, Screen.LINK_RESULTS)
        currentScreen = Screen.LINK_RESULTS
    }

    fun openEditLink(link: Link) {
        val fragment = AddLinkFragment.newInstance(link)
        open(fragment, Screen.ADD_LINK)
        currentScreen = Screen.ADD_LINK
    }

    fun openFragment(screen: Screen) {
        if (currentScreen != screen) {
            val fragment = getFragmentForScreen(screen)
            if (fragment != null) {
                open(fragment, screen)
                currentScreen = screen
            }
        }
    }

    fun navigateBack(activity: Activity) {
        if (currentScreen == Screen.HOME) {
            activity.finish()
        } else {
            openFragment(Screen.HOME)
        }
    }

    private fun open(fragment: Fragment, screen: Screen) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(screen.name)
            .commit()
    }

    private fun getFragmentForScreen(screen: Screen): Fragment? {
        return when (screen) {
            Screen.HOME -> HomeFragment.newInstance()
            Screen.ADD_LINK -> AddLinkFragment.newInstance()
            Screen.MY_ACCOUNT -> MyAccountFragment.newInstance()
            Screen.SETTINGS -> SettingsFragment.newInstance()
            else -> null
        }
    }
}