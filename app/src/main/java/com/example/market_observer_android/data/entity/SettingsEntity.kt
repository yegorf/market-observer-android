package com.example.market_observer_android.data.entity

data class SettingsEntity(
    var appNotificationsOn: Boolean = true,
    var emailNotificationsOn: Boolean = false,
    var observeNewLinkOn: Boolean = false,
    var autoBackupOn: Boolean = true
)