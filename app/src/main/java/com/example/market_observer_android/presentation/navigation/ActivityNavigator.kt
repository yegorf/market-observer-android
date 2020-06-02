package com.example.market_observer_android.presentation.navigation

import android.app.Activity
import android.content.Intent
import com.example.market_observer_android.presentation.activity.GlobalActivity
import com.example.market_observer_android.presentation.activity.LoginActivity

object ActivityNavigator {

    fun navigateToMainActivity(activity: Activity) {
        activity.startActivity(Intent(activity, GlobalActivity::class.java))
        activity.finish()
    }

    fun navigateToLoginActivity(activity: Activity) {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }
}