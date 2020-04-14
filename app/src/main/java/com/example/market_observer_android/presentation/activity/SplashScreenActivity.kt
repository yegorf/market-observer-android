package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                ActivityNavigator.navigateToMainActivity(this)
            } else {
                ActivityNavigator.navigateToLoginActivity(this)
            }
        }, 1000)
    }
}