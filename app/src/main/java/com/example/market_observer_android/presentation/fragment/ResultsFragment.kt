package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : BaseFragment() {

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
            //todo replace with edit link screen
            FragmentNavigator(activity!!.supportFragmentManager).openFragment(FragmentNavigator.SCREEN_ADD_LINK)
        }

        if (results.isEmpty()) {
            results_container.visibility = View.GONE
        } else {
            tv_no_results.visibility = View.GONE
        }
    }
}