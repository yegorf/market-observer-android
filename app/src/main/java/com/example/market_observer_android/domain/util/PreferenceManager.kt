package com.example.market_observer_android.domain.util

import android.content.Context
import android.content.SharedPreferences
import com.example.market_observer_android.common.application.Application

object PreferenceManager {

    private const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
    private const val NOTIFICATIONS_ON = "NOTIFICATIONS_ON"
    private const val EMAIL_NOTIFICATIONS_ON = "EMAIL_NOTIFICATIONS_ON"

    fun setNotificationsOn(isNotificationsOn: Boolean) {
        getPreference().edit().putBoolean(NOTIFICATIONS_ON, isNotificationsOn).apply()
    }

    fun isNotificationsOn(): Boolean {
        return getPreference().getBoolean(NOTIFICATIONS_ON, true)
    }

    fun setEmailNotificationsOn(isNotificationsOn: Boolean) {
        getPreference().edit().putBoolean(EMAIL_NOTIFICATIONS_ON, isNotificationsOn).apply()
    }

    fun isEmailNotificationsOn(): Boolean {
        return getPreference().getBoolean(EMAIL_NOTIFICATIONS_ON, true)
    }

    private fun getPreference(): SharedPreferences {
        return Application.getInstance()
            .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}