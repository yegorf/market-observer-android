package com.example.market_observer_android.presentation.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.fragment.HomeFragment

class FragmentNavigator(private val fragmentManager: androidx.fragment.app.FragmentManager) {

    companion object {
        const val SCREEN_HOME = "SCREEN_HOME"
        var currentScreen = SCREEN_HOME
    }

    fun openHome() {
        val fragment = getFragmentForScreen(SCREEN_HOME)
        if (fragment != null) {
            open(fragment, SCREEN_HOME)
            currentScreen = SCREEN_HOME
        }
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
            else -> null
        }
    }
}