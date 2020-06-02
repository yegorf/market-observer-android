package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.mvp_view.SavedResultsView
import com.example.market_observer_android.presentation.presenter.SavedResultsPresenter
import kotlinx.android.synthetic.main.fragment_saved_results.view.*
import javax.inject.Inject

class SavedResultsFragment : BaseFragment(), SavedResultsView,
    LinkResultAdapter.LinkResultListener {

    @Inject
    lateinit var presenter: SavedResultsPresenter
    private val adapter = LinkResultAdapter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var noResultsTv: TextView
    private lateinit var savedResultsRecycler: RecyclerView

    companion object {
        fun newInstance(): SavedResultsFragment {
            return SavedResultsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_results, container, false)
        getComponent().inject(this)
        presenter.onCreate(this)
        init(view)
        setToolbarTitle(getString(R.string.saved_results_title), null)
        return view
    }

    override fun onPause() {
        super.onPause()
        presenter.onDestroy()
    }

    private fun init(view: View) {
        initViews(view)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        savedResultsRecycler.layoutManager = manager
        savedResultsRecycler.adapter = adapter
        presenter.getSavedResults()
    }

    private fun initViews(view: View) {
        progressBar = view.saved_results_progress_bar
        noResultsTv = view.tv_no_saved_results
        savedResultsRecycler = view.rv_saved_results
    }

    override fun hasNavigationArrow() = false

    override fun setSavedResults(results: List<LinkResult>) {
        progressBar.visibility = View.GONE
        if (results.isNotEmpty()) {
            adapter.setData(results)
            noResultsTv.visibility = View.GONE
        } else {
            noResultsTv.visibility = View.VISIBLE
        }
    }

    override fun onResultClick(result: LinkResult) {

    }

    override fun onResultSave(result: LinkResult) {
        presenter.saveResult(result)
    }

    override fun onResultUnsave(result: LinkResult) {
        presenter.deleteSavedResult(result)
    }
}