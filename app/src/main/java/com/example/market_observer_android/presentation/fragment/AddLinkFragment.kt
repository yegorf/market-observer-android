package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.presenter.AddLinkPresenter
import com.example.market_observer_android.presentation.view.AddLinkView
import javax.inject.Inject

class AddLinkFragment : BaseFragment(), AddLinkView {

    @Inject
    lateinit var presenter: AddLinkPresenter

    companion object {
        fun newInstance(): AddLinkFragment {
            return AddLinkFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        return inflater.inflate(R.layout.fragment_add_link, container, false)
    }
}