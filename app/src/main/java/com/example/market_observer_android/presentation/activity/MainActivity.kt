package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.service.MonitoringService
import com.example.market_observer_android.presentation.navigation.FragmentNavigator


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MonitoringService.startService(this)
        FragmentNavigator(supportFragmentManager).openHome()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun showArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun hideArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FragmentNavigator(supportFragmentManager).navigateBack(this)
    }
}
