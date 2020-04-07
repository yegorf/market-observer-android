package com.example.market_observer_android.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.LinkDetailPresenter
import kotlinx.android.synthetic.main.fragment_link_detail.*
import javax.inject.Inject

class LinkDetailFragment : BaseFragment(), LinkDetailView,
    LinkResultAdapter.LinkResultListener {

    @Inject
    lateinit var presenter: LinkDetailPresenter
    private val adapter = LinkResultAdapter(this)

    companion object {
        private const val LINK_ARG_KEY = "LINK_ARG_KEY"

        fun newInstance(link: ActiveLink): LinkDetailFragment {
            val fragment = LinkDetailFragment()
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
        getComponent().inject(this)
        presenter.onCreate(this)
        return inflater.inflate(R.layout.fragment_link_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate(this)
        val link = arguments?.getSerializable(LINK_ARG_KEY) as ActiveLink
        init(link)
    }

    private fun init(activeLink: ActiveLink) {
        val link = activeLink.link
        val results = activeLink.results

        tv_name.text = link.name
        tv_url.text = link.url
        tv_results_count.text = results.size.toString()

        btn_edit.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).openEditLink(link)
        }
        btn_delete.setOnClickListener {
            if (link.url != null) {
                presenter.deleteLink(link.url as String)
            }
        }

        if (results.isEmpty()) {
            results_container.visibility = View.GONE
        } else {
            tv_no_results.visibility = View.GONE

            rv_link_results.layoutManager = LinearLayoutManager(context)
            rv_link_results.adapter = adapter
            adapter.setData(results)
        }
    }

    override fun onDeleteLink() {
        FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
    }

    override fun onResultClick(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        activity!!.startActivity(intent)
    }
}