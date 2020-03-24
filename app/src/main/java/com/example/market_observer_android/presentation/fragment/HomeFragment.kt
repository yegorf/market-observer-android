package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.adapter.LinkAdapter
import com.example.market_observer_android.presentation.presenter.HomePresenter
import com.example.market_observer_android.presentation.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    private var adapter = LinkAdapter()

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_active_links.layoutManager = LinearLayoutManager(context)
        rv_active_links.adapter = adapter

        presenter.onCreate(this)
        presenter.getActiveLinks()
    }

    override fun setActiveLinks(links: List<ActiveLink>?) {
        adapter.setData(links)
    }
}