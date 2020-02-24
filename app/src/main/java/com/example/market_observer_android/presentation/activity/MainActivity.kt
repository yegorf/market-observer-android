package com.example.market_observer_android.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.navigation.FragmentNavigator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentNavigator(supportFragmentManager)
            .openFragment(FragmentNavigator.SCREEN_HOME)
    }
}
