package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.common.event.Event
import com.example.market_observer_android.common.event.RxBus
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.adapter.LinkAdapter
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.HomePresenter
import com.example.market_observer_android.presentation.mvp_view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, LinkAdapter.LinkAdapterListener {

    @Inject
    lateinit var presenter: HomePresenter
    private var adapter = LinkAdapter(this)
    private val bus = RxBus

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        presenter.onCreate(this)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_active_links.layoutManager = LinearLayoutManager(context)
        rv_active_links.adapter = adapter

        btn_add_link.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager)
                .openFragment(FragmentNavigator.SCREEN_ADD_LINK)
        }

        btn_remove_all_links.setOnClickListener {
            bus.sendEvent(Event.REMOVE_ALL_LINK_FROM_OBSERVE)
        }

        presenter.getActiveLinks()
    }

    override fun setActiveLinks(links: List<ActiveLink>?) {
        val results = mutableListOf<LinkResult>()
        val link = LinkResult()
        link.imageUrl = "https://apollo-ireland.akamaized.net:443/v1/files/drtm269sj3kw-UA/image;s=644x461"
        link.title = "IPHONE 112"
        link.url = "https://www.olx.ua/obyavlenie/chehol-silikon-iphone-11-pro-max-mi9t-redmi-7-8a-note-8-pro-p-smart-z-IDG0wYY.html?sd=1#3c2df8246d;promoted"
        link.location = "Kyiv, Ukraine"
        results.add(link)
        //todo mocked
        links?.get(0)?.results = results

        adapter.setData(links)
    }

    override fun onLinkClick(link: ActiveLink) {
        FragmentNavigator(activity!!.supportFragmentManager).openLinkDetails(link)
    }

    override fun onLinkLongClick(link: ActiveLink) {
        FragmentNavigator(activity!!.supportFragmentManager).openEditLink(link.link)
    }
}