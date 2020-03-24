package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.presenter.AddLinkPresenter
import com.example.market_observer_android.presentation.view.AddLinkView
import kotlinx.android.synthetic.main.fragment_add_link.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_add_link.setOnClickListener {
            val name = et_name.text.toString()
            val url = et_url.text.toString()
            val periodicity = et_periodicity.text.toString().toInt()

            presenter.addLink(url, name, periodicity)
        }
        btn_cancel_link.setOnClickListener {

        }
    }

    override fun onSuccess() {

    }

    override fun onFail() {

    }
}