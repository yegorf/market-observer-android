package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.service.MonitoringService
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MonitoringService.startService(this)
        FragmentNavigator(supportFragmentManager).openHome()

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("jija", currentUser.email!!)
        } else {
            Log.d("jija", "no user")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FragmentNavigator(supportFragmentManager).navigateBack(this)
    }
}
