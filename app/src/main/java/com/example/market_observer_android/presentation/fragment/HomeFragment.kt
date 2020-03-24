package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.ActiveLink
import com.example.market_observer_android.presentation.presenter.HomePresenter
import com.example.market_observer_android.presentation.view.HomeView
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

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
        presenter.getActiveLinks()
    }

    override fun setActiveLinks(links: List<ActiveLink>) {

    }
}