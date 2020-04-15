package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.mvp_view.MyAccountView
import com.example.market_observer_android.presentation.presenter.MyAccountPresenter
import javax.inject.Inject

class MyAccountFragment : BaseFragment(), MyAccountView {

    @Inject
    lateinit var presenter: MyAccountPresenter

    companion object {
        fun newInstance() = MyAccountFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getComponent().inject(this)
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate(this)
    }

    override fun hasNavigationArrow() = false
}