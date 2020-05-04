package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.BuildConfig
import com.example.market_observer_android.R
import com.example.market_observer_android.common.util.AssetsManager
import com.example.market_observer_android.presentation.adapter.MarketAdapter
import com.example.market_observer_android.presentation.mvp_view.InfoView
import kotlinx.android.synthetic.main.fragment_info.view.*

class InfoFragment : BaseFragment(), InfoView {

    private lateinit var versionText: TextView
    private lateinit var marketsRecyclerView: RecyclerView

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
        initViews(view)
        init()
        return view
    }

    private fun initViews(view: View) {
        versionText = view.tv_info_version
        marketsRecyclerView = view.rv_info_market_list
    }

    private fun init() {
        versionText.text = BuildConfig.VERSION_NAME
        marketsRecyclerView.layoutManager = LinearLayoutManager(context)
        marketsRecyclerView.adapter = MarketAdapter(AssetsManager.getMarketsList(context!!))
    }

    override fun hasNavigationArrow() = false
}