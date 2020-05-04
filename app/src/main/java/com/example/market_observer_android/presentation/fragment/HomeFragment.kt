package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.adapter.LinkAdapter
import com.example.market_observer_android.presentation.mvp_view.HomeView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, LinkAdapter.LinkAdapterListener {

    @Inject
    lateinit var presenter: HomePresenter
    private var adapter = LinkAdapter(this)

    private lateinit var linksRecycler: RecyclerView
    private lateinit var emptyListText: TextView
    private lateinit var linksProgressBar: ProgressBar

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
        initViews(view)
        getComponent().inject(this)
        presenter.onCreate(this)
        init()
        return view
    }

    private fun initViews(view: View) {
        linksRecycler = view.rv_active_links
        emptyListText = view.tv_list_empty
        linksProgressBar = view.links_progress_bar
    }

    private fun init() {
        linksRecycler.layoutManager = LinearLayoutManager(context)
        linksRecycler.adapter = adapter
        presenter.getActiveLinks()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setActiveLinks(links: List<Link>?) {
        if (links != null && links.isNotEmpty()) {
            emptyListText.visibility = View.GONE
            adapter.setData(links)
        } else {
            emptyListText.visibility = View.VISIBLE
        }
        linksProgressBar.visibility = View.GONE
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