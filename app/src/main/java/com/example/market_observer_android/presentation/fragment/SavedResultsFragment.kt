package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.LinkResult
import com.example.market_observer_android.presentation.adapter.LinkResultAdapter
import com.example.market_observer_android.presentation.mvp_view.SavedResultsView
import com.example.market_observer_android.presentation.presenter.SavedResultsPresenter
import kotlinx.android.synthetic.main.fragment_saved_results.*
import kotlinx.android.synthetic.main.fragment_saved_results.view.*
import javax.inject.Inject

class SavedResultsFragment : BaseFragment(), SavedResultsView,
    LinkResultAdapter.LinkResultListener {

    @Inject
    lateinit var presenter: SavedResultsPresenter
    private val adapter = LinkResultAdapter(this)

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

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun init(view: View) {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.rv_saved_results.layoutManager = manager
        view.rv_saved_results.adapter = adapter
        presenter.getSavedResults()
    }

    override fun hasNavigationArrow() = false

    override fun setSavedResults(results: List<LinkResult>) {
        adapter.setData(results)
        saved_results_progress_bar.visibility = View.GONE
    }

    override fun onResultClick(result: LinkResult) {

    }

    override fun onResultSave(result: LinkResult) {
        presenter.deleteSavedResult(result)
    }
}