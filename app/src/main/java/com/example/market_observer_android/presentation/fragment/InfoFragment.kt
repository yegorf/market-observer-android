package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.BuildConfig
import com.example.market_observer_android.R
import com.example.market_observer_android.common.util.AssetsManager
import com.example.market_observer_android.presentation.mvp_view.InfoView
import kotlinx.android.synthetic.main.fragment_info.view.*

class InfoFragment : BaseFragment(), InfoView {

    companion object {
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.tv_info_version.text = BuildConfig.VERSION_NAME
        view.tv_market_list.text = AssetsManager.getMarketsList(context!!)
            .map { it.name }
            .reduce { n1, n2 -> n1 + "\n" + n2 }
    }

    override fun hasNavigationArrow() = false
}