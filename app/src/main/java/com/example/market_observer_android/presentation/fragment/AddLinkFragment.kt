package com.example.market_observer_android.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market_observer_android.R
import com.example.market_observer_android.common.util.AssetsManager
import com.example.market_observer_android.domain.model.Link
import com.example.market_observer_android.domain.parser.MarketParserFactory
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.adapter.MarketAdapter
import com.example.market_observer_android.presentation.mvp_view.AddLinkView
import com.example.market_observer_android.presentation.navigation.FragmentNavigator
import com.example.market_observer_android.presentation.presenter.AddLinkPresenter
import com.example.market_observer_android.presentation.util.showShortToast
import kotlinx.android.synthetic.main.fragment_add_link.*
import kotlinx.android.synthetic.main.fragment_add_link.view.*
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

    override fun hasNavigationArrow() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_link, container, false)
        getComponent().inject(this)
        presenter.onCreate(this)
        init(view)
        return view
    }

    private fun init(view: View) {
        view.rv_add_link_market_list.layoutManager = LinearLayoutManager(context)
        view.rv_add_link_market_list.adapter = MarketAdapter(AssetsManager.getMarketsList(context!!))

        view.et_url.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Thread {
                    try {
                        val url = view.et_url.getText()
                        val parser = MarketParserFactory.createParser(url)
                        if (parser != null) {
                            val title = parser.parseTitle(url)
                            activity!!.runOnUiThread {
                                if (title.isNotEmpty()) {
                                    view.et_name.setText(title)
                                    view.tv_market.text = parser.getMarketName()
                                    view.iv_status.setImageResource(R.drawable.ic_success)
                                } else {
                                    view.tv_market.text = getString(R.string.invalid_url)
                                    view.iv_status.setImageResource(R.drawable.ic_error)
                                }
                            }
                        } else {
                            activity!!.runOnUiThread {
                                view.tv_market.text = getString(R.string.invalid_url)
                                view.iv_status.setImageResource(R.drawable.ic_error)
                            }
                        }
                    } catch (_: Exception) {

                    }
                }.start()
            }
        })

        val link = arguments?.getSerializable(LINK_ARG_KEY) as Link?
        val isEdit = link != null

        val spinnerValues = arrayOf(5, 30, 60)
        val adapter =
            ArrayAdapter<Int>(
                context as Context,
                android.R.layout.simple_spinner_item,
                spinnerValues
            )
        view.spinner_periodicity.adapter = adapter
        view.switch_observe.isChecked = PreferenceManager.isObserveNewLink()
        view.btn_add_link.setOnClickListener {
            try {
                val name = et_name.getText()
                val url = et_url.getText()

                if (validateInputs(name, url)) {
                    val periodicity = spinner_periodicity.selectedItem as Int
                    val isObserve = switch_observe.isChecked
                    val link = Link(url, name, periodicity, isObserve)
                    if (!isEdit) {
                        presenter.addLink(link)
                    } else {
                        presenter.editLink(link)
                    }
                } else {
                    showShortToast(R.string.invalid_data)
                }
            } catch (e: Exception) {
                showShortToast(R.string.invalid_data)
            }
        }
        view.btn_cancel_link.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
        }

        if (isEdit) {
            setEditData(view, link!!)
        }
    }

    private fun validateInputs(name: String, url: String): Boolean {
        return name.isNotEmpty() && url.isNotEmpty()
    }

    private fun setEditData(view: View, link: Link) {
        view.et_name.setText(link.name!!)
        view.et_url.setText(link.url!!)

        val selection = when (link.periodicity) {
            5 -> 0
            30 -> 1
            60 -> 2
            else -> 0
        }
        view.spinner_periodicity.setSelection(selection)
    }

    override fun onSuccess() {
        FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
    }

    override fun onFail() {

    }
}