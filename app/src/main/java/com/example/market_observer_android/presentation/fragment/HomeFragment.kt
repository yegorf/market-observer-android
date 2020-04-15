package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.adapter.LinkAdapter
import com.example.market_observer_android.presentation.mvp_view.HomeView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_add_link.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, LinkAdapter.LinkAdapterListener {

    @Inject
    lateinit var presenter: HomePresenter
    private var adapter = LinkAdapter(this)

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        getComponent().inject(this)
        presenter.onCreate(this)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.rv_active_links.layoutManager = LinearLayoutManager(context)
        view.rv_active_links.adapter = adapter
        presenter.getActiveLinks()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setActiveLinks(links: List<ActiveLink>?) {
        if (links != null && links.isNotEmpty()) {
            tv_list_empty.visibility = View.GONE
            adapter.setData(links)
        } else {
            tv_list_empty.visibility = View.VISIBLE
        }
    }

    override fun onLinkClick(link: ActiveLink) {
        FragmentNavigator(activity!!.supportFragmentManager).openLinkDetails(link)
    }

    override fun onLinkLongClick(link: ActiveLink) {
        FragmentNavigator(activity!!.supportFragmentManager).openEditLink(link.link)
    }

    override fun onEyeClick(link: ActiveLink) {
        presenter.updateLink(link)
    }

    override fun hasNavigationArrow() = false
}