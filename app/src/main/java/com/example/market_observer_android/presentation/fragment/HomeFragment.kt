package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.adapter.LinkAdapter
import com.example.market_observer_android.presentation.mvp_view.HomeView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_progress.*
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

    override fun setActiveLinks(links: List<Link>?) {
        if (links != null && links.isNotEmpty()) {
            tv_list_empty.visibility = View.GONE
            adapter.setData(links)
        } else {
            tv_list_empty.visibility = View.VISIBLE
        }
        links_progress_bar.visibility = View.GONE
    }

    override fun onLinkClick(link: Link) {
        FragmentNavigator(activity!!.supportFragmentManager).openLinkDetails(link)
    }

    override fun onLinkLongClick(link: Link) {
        FragmentNavigator(activity!!.supportFragmentManager).openEditLink(link)
    }

    override fun onEyeClick(link: Link) {
        presenter.updateLink(link)
    }

    override fun hasNavigationArrow() = false
}