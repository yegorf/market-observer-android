package com.example.market_observer_android.common.application

import android.app.Application
import com.example.market_observer_android.common.injection.ApplicationComponent
import com.example.market_observer_android.common.injection.ApplicationModule
import com.example.market_observer_android.common.injection.DaggerApplicationComponent
import io.realm.Realm
import io.realm.RealmConfiguration


class Application : Application() {

    lateinit var component: ApplicationComponent

    companion object {
        private lateinit var instance:
                com.example.market_observer_android.common.application.Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDaggerConfig()
        initRealmConfiguration()
    }

    private fun initDaggerConfig() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private fun initRealmConfiguration() {
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    fun getApplicationComponent() = component
}