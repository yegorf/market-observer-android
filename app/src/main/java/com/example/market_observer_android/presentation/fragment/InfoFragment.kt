package com.example.market_observer_android.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.market_observer_android.presentation.presenter.InfoPresenter
import kotlinx.android.synthetic.main.fragment_info.view.*
import javax.inject.Inject

class InfoFragment : BaseFragment(), InfoView {

    @Inject
    lateinit var presenter: InfoPresenter

    private lateinit var versionText: TextView
    private lateinit var marketsRecyclerView: RecyclerView
    private lateinit var contactUs: View

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
        getComponent().inject(this)
        presenter.onCreate(this)
        initViews(view)
        init()
        setListeners()
        return view
    }

    private fun initViews(view: View) {
        versionText = view.tv_info_version
        marketsRecyclerView = view.rv_info_market_list
        contactUs = view.contact_us_section
    }

    private fun init() {
        versionText.text = BuildConfig.VERSION_NAME
        marketsRecyclerView.layoutManager = LinearLayoutManager(context)
        marketsRecyclerView.adapter = MarketAdapter(AssetsManager.getMarketsList(context!!))
    }

    private fun setListeners() {
        contactUs.setOnClickListener {
            presenter.contactUs(context!!)
        }
    }

    override fun hasNavigationArrow() = false
}