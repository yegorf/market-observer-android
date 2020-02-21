package com.example.market_observer_android.presentation.navigation

import android.app.Activity
import android.content.Intent
import com.example.market_observer_android.presentation.activity.MainActivity

object ActivityNavigator {

    fun navigateToMainActivity(activity: Activity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}