package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.service.MonitoringService
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MonitoringService.startService(this)
        FragmentNavigator(supportFragmentManager).openHome()
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar_iv_add_link.setOnClickListener {
            FragmentNavigator(supportFragmentManager).openFragment(FragmentNavigator.Screen.ADD_LINK)
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
