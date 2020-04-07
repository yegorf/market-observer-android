package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_home.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).openHome()
        }
    }
}