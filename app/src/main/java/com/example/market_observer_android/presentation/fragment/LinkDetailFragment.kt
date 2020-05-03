package com.example.market_observer_android.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.activity.MainActivity
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.LinkDetailPresenter
import kotlinx.android.synthetic.main.fragment_link_detail.*
import kotlinx.android.synthetic.main.fragment_progress.*
import javax.inject.Inject


class LinkDetailFragment : BaseFragment(), LinkDetailView,
    LinkResultAdapter.LinkResultListener {

    @Inject
    lateinit var presenter: LinkDetailPresenter
    private val adapter = LinkResultAdapter(this)

    companion object {
        private const val LINK_ARG_KEY = "LINK_ARG_KEY"

        fun newInstance(link: Link): LinkDetailFragment {
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
        val link = arguments?.getSerializable(LINK_ARG_KEY) as Link
        (activity as MainActivity).setToolbarTitle(link.name!!, link.url!!)
        init(link)
    }

    private fun init(activeLink: Link) {
        btn_edit.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).openEditLink(activeLink)
        }
        btn_delete.setOnClickListener {
            if (activeLink.url != null) {
                presenter.deleteLink(activeLink.url as String)
            }
        }

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_link_results.layoutManager = manager
        rv_link_results.adapter = adapter
        presenter.getResults(activeLink.url!!)
    }

    override fun onDeleteLink() {
        FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
    }

    override fun setResults(results: List<LinkResult>) {
        adapter.setData(results)
        results_progress_bar.visibility = View.GONE
    }

    override fun onResultClick(result: LinkResult) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(result.url)
        activity!!.startActivity(intent)
    }

    override fun onResultSave(result: LinkResult) {
        presenter.saveResult(result)
    }

    override fun onResultUnsave(result: LinkResult) {
        presenter.unsaveResult(result)
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).removeToolbarTitle()
    }

    override fun hasNavigationArrow() = true
}