package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.AddLinkPresenter
import com.example.market_observer_android.presentation.view.AddLinkView
import kotlinx.android.synthetic.main.fragment_add_link.*
import javax.inject.Inject

class AddLinkFragment : BaseFragment(), AddLinkView {

    @Inject
    lateinit var presenter: AddLinkPresenter

    companion object {
        private const val LINK_ARG_KEY = "LINK_ARG_KEY"

        fun newInstance(): AddLinkFragment {
            return AddLinkFragment()
        }

        fun newInstance(link: Link): AddLinkFragment {
            val fragment = AddLinkFragment()
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
        component.inject(this)
        return inflater.inflate(R.layout.fragment_add_link, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_add_link.setOnClickListener {
            try {
                val name = et_name.text.toString()
                val url = et_url.text.toString()
                val periodicity = et_periodicity.text.toString().toInt()

                presenter.addLink(url, name, periodicity)
            } catch (e: Exception) {
                Toast.makeText(context, "Invalid data!", Toast.LENGTH_SHORT).show()
            }
        }
        btn_cancel_link.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
        }
    }

    override fun onSuccess() {

    }

    override fun onFail() {

    }
}