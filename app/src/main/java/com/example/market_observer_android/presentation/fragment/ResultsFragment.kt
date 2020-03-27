package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : BaseFragment() {

    lateinit var adapter: LinkResultAdapter

    companion object {
        private const val LINK_ARG_KEY = "LINK_ARG_KEY"

        fun newInstance(link: ActiveLink): ResultsFragment {
            val fragment = ResultsFragment()
            val args = Bundle()
            args.putSerializable(LINK_ARG_KEY, link)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = arguments?.getSerializable(LINK_ARG_KEY) as ActiveLink
        init(link)
    }

    private fun init(activeLink: ActiveLink) {
        val link = activeLink.link
        val results = activeLink.results

        tv_name.text = link.name
        tv_url.text = link.url

        btn_edit.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).openEditLink(link)
        }

        if (results.isEmpty()) {
            results_container.visibility = View.GONE
        } else {
            tv_no_results.visibility = View.GONE

            rv_active_links.layoutManager = LinearLayoutManager(context)
            rv_active_links.adapter = adapter
            adapter.setData(results)
        }
    }
}