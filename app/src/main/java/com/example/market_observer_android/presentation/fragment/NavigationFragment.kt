package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import kotlinx.android.synthetic.main.fragment_navigation.view.*

class NavigationFragment : BaseFragment() {

    private lateinit var list: List<View>

    private lateinit var homeButton: ImageView
    private lateinit var historyButton: ImageView
    private lateinit var settingsButton: ImageView
    private lateinit var infoButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_navigation, container, false)
        initViews(view)
        chooseSection(homeButton)
        setOnClickListeners()
        return view
    }

    private fun initViews(view: View) {
        homeButton = view.btn_home
        historyButton = view.btn_settings
        settingsButton = view.btn_history
        infoButton = view.btn_info
        list = listOf(homeButton, historyButton, settingsButton, infoButton)
    }

    private fun setOnClickListeners() {
        homeButton.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openHome()
        }
        historyButton.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openFragment(FragmentNavigator.Screen.SETTINGS)
        }
        settingsButton.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openFragment(FragmentNavigator.Screen.SAVED_RESULTS)
        }
        infoButton.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openFragment(FragmentNavigator.Screen.INFO)
        }
    }

    private fun chooseSection(view: View) {
        list.forEach {
            if (it == view) {
                it.setBackgroundResource(R.drawable.selected)
            } else {
                it.setBackgroundResource(0)
            }
        }
    }

    override fun hasNavigationArrow() = false
}