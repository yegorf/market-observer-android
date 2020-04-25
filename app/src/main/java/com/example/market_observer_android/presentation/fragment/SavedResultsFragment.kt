package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.mvp_view.SavedResultsView
import com.example.market_observer_android.presentation.presenter.SavedResultsPresenter
import kotlinx.android.synthetic.main.fragment_progress.view.*
import kotlinx.android.synthetic.main.fragment_saved_results.*
import kotlinx.android.synthetic.main.fragment_saved_results.view.*
import javax.inject.Inject

class SavedResultsFragment : BaseFragment(), SavedResultsView,
    LinkResultAdapter.LinkResultListener {

    @Inject
    lateinit var presenter: SavedResultsPresenter
    private val adapter = LinkResultAdapter(this)
    private lateinit var progressBar: ProgressBar

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
        return view
    }

    override fun onPause() {
        super.onPause()
        presenter.onDestroy()
    }

    private fun init(view: View) {
        initViews(view)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.rv_saved_results.layoutManager = manager
        view.rv_saved_results.adapter = adapter
        presenter.getSavedResults()
    }

    private fun initViews(view: View) {
        progressBar = view.saved_results_progress_bar
    }

    override fun hasNavigationArrow() = false

    override fun setSavedResults(results: List<LinkResult>) {
        adapter.setData(results)
        progressBar.visibility = View.GONE
    }

    override fun onResultClick(result: LinkResult) {

    }

    override fun onResultSave(result: LinkResult) {
        presenter.deleteSavedResult(result)
    }
}