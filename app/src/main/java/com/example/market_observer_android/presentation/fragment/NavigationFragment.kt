package com.example.market_observer_android.presentation.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseFragment() {

    private val list = mutableListOf<View>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.add(btn_home)
        list.add(btn_account)
        list.add(btn_history)
        list.add(btn_settings)

        chooseSection(btn_home)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_home.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openHome()
        }
        btn_account.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openFragment(FragmentNavigator.Screen.MY_ACCOUNT)
        }
        btn_settings.setOnClickListener {
            chooseSection(it)
            FragmentNavigator(activity!!.supportFragmentManager).openFragment(FragmentNavigator.Screen.SETTINGS)
        }
        btn_history.setOnClickListener {
            chooseSection(it)
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