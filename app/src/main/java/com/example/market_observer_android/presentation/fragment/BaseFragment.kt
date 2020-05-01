package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.presentation.activity.MainActivity
import com.example.market_observer_android.presentation.injection.DaggerPresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationModule

abstract class BaseFragment : Fragment() {

    private lateinit var component: PresentationComponent

    protected fun getComponent() = component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerPresentationComponent.builder()
            .dataModule(DataModule())
            .presentationModule(PresentationModule())
            .build()
    }

    override fun onResume() {
        super.onResume()
        if (hasNavigationArrow()) {
            (activity as MainActivity).showArrow()
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).hideArrow()
    }

    abstract fun hasNavigationArrow(): Boolean
}