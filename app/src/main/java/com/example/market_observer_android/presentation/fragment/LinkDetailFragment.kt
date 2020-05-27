package com.example.market_observer_android.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.activity.MainActivity
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.mvp_view.LinkDetailView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.LinkDetailPresenter
import kotlinx.android.synthetic.main.fragment_link_detail.view.*
import javax.inject.Inject


class LinkDetailFragment : BaseFragment(), LinkDetailView,
    LinkResultAdapter.LinkResultListener {

    @Inject
    lateinit var presenter: LinkDetailPresenter
    private val adapter = LinkResultAdapter(this)

    private lateinit var resultsRecycler: RecyclerView
    private lateinit var editButton: ImageView
    private lateinit var deleteButton: ImageView
    private lateinit var resultsProgressBar: ProgressBar

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
        val view = inflater.inflate(R.layout.fragment_link_detail, container, false)
        initViews(view)
        getComponent().inject(this)
        presenter.onCreate(this)
        val link = arguments?.getSerializable(LINK_ARG_KEY) as Link
        if (activity is MainActivity) {
            (activity as MainActivity).setToolbarTitle(link.name!!, link.url!!)
        }
        init(link)
        return view
    }

    private fun initViews(view: View) {
        resultsRecycler = view.rv_link_results
        editButton = view.btn_edit
        deleteButton = view.btn_delete
        resultsProgressBar = view.results_progress_bar
    }

    private fun init(activeLink: Link) {
        editButton.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).openEditLink(activeLink)
        }
        deleteButton.setOnClickListener {
            if (activeLink.url != null) {
                presenter.deleteLink(activeLink.url as String)
            }
        }

        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        resultsRecycler.layoutManager = manager
        resultsRecycler.adapter = adapter
        presenter.getResults(activeLink.url!!)
    }

    override fun onDeleteLink() {
        FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
    }

    override fun setResults(results: List<LinkResult>) {
        adapter.setData(results)
        resultsProgressBar.visibility = View.GONE
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
        if (activity is MainActivity) {
            (activity as MainActivity).removeToolbarTitle()
        }
    }

    override fun hasNavigationArrow() = true
}