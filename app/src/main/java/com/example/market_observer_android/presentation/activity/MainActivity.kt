package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.service.MonitoringService
import com.example.market_observer_android.presentation.navigation.FragmentNavigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MonitoringService.startService(this)
        FragmentNavigator(supportFragmentManager).openHome()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FragmentNavigator(supportFragmentManager).navigateBack(this)
    }
}
