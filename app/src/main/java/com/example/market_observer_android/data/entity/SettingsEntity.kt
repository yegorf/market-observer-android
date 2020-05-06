package com.example.market_observer_android.data.entity

data class SettingsEntity(
    var appNotificationsOn: Boolean = true,
    var emailNotificationsOn: Boolean = false,
    var observeNewLinkOn: Boolean = false,
    var autoBackupOn: Boolean = true
) {
    companion object {
        const val NAME: String = "settings"
        const val APP_NOTIFICATIONS: String = "appNotificationsOn"
        const val EMAIL_NOTIFICATIONS: String = "emailNotificationsOn"
        const val OBSERVE_NEW_LINK: String = "observeNewLinkOn"
        const val AUTO_BACKUP: String = "autoBackupOn"
        const val USER_UID: String = "userUid"
    }
}