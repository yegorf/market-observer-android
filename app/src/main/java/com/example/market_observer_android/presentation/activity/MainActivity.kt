package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.service.MonitoringService
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_link_result.view.*


class MainActivity : BaseActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        MonitoringService.startService(this)
        FragmentNavigator(supportFragmentManager).openHome()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
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

    fun setToolbarTitle(title: String, subtitle: String?) {
        toolbar.tv_toolbar_title.text = title
        if (subtitle != null) {
            toolbar.tv_toolbar_subtitle.visibility = View.VISIBLE
            toolbar.tv_toolbar_subtitle.text = subtitle
        }
    }

    fun removeToolbarTitle() {
        toolbar.tv_toolbar_title.text = resources.getString(R.string.app_name)
        toolbar.tv_toolbar_subtitle.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FragmentNavigator(supportFragmentManager).navigateBack(this)
    }
}
