package com.example.market_observer_android.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_observer_android.BuildConfig
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
import com.example.market_observer_android.presentation.view.StyledButton
import com.example.market_observer_android.presentation.view.StyledEditText
import kotlinx.android.synthetic.main.fragment_add_link.view.*
import javax.inject.Inject

class AddLinkFragment : BaseFragment(), AddLinkView {

    @Inject
    lateinit var presenter: AddLinkPresenter

    private lateinit var marketsRecycler: RecyclerView
    private lateinit var statusImage: ImageView
    private lateinit var nameInput: StyledEditText
    private lateinit var urlInput: StyledEditText
    private lateinit var marketTextView: TextView
    private lateinit var periodicitySpinner: Spinner
    private lateinit var observeSwitch: Switch
    private lateinit var addLinkButton: StyledButton
    private lateinit var cancelLinkButton: StyledButton

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
        initViews(view)
        getComponent().inject(this)
        presenter.onCreate(this)
        init()
        return view
    }

    private fun initViews(view: View) {
        marketsRecycler = view.rv_add_link_market_list
        statusImage = view.iv_status
        nameInput = view.et_name
        urlInput = view.et_url
        marketTextView = view.tv_market
        periodicitySpinner = view.spinner_periodicity
        observeSwitch = view.switch_observe
        addLinkButton = view.btn_add_link
        cancelLinkButton = view.btn_cancel_link
    }

    private fun init() {
        marketsRecycler.layoutManager = LinearLayoutManager(context)
        marketsRecycler.adapter = MarketAdapter(AssetsManager.getMarketsList(context!!))

        urlInput.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Thread {
                    try {
                        val url = urlInput.getText()
                        val parser = MarketParserFactory.createParser(url)
                        if (parser != null) {
                            val title = parser.parseTitle(url)
                            activity!!.runOnUiThread {
                                if (title.isNotEmpty()) {
                                    nameInput.setText(title)
                                    marketTextView.text = parser.getMarketName()
                                    statusImage.setImageResource(R.drawable.ic_success)
                                } else {
                                    marketTextView.text = getString(R.string.invalid_url)
                                    statusImage.setImageResource(R.drawable.ic_error)
                                }
                            }
                        } else {
                            activity!!.runOnUiThread {
                                marketTextView.text = getString(R.string.invalid_url)
                                statusImage.setImageResource(R.drawable.ic_error)
                            }
                        }
                    } catch (e: Exception) {
                        if (BuildConfig.DEBUG) {
                            Log.e(tag, e.message)
                        }
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
        periodicitySpinner.adapter = adapter
        observeSwitch.isChecked = PreferenceManager.isObserveNewLink()
        addLinkButton.setOnClickListener {
            try {
                val name = nameInput.getText()
                val url = urlInput.getText()

                if (validateInputs(name, url)) {
                    val periodicity = periodicitySpinner.selectedItem as Int
                    val isObserve = observeSwitch.isChecked
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
        cancelLinkButton.setOnClickListener {
            FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
        }

        if (isEdit) {
            setEditData(link!!)
        }
    }

    private fun validateInputs(name: String, url: String): Boolean {
        return name.isNotEmpty() && url.isNotEmpty()
    }

    private fun setEditData(link: Link) {
        nameInput.setText(link.name!!)
        urlInput.setText(link.url!!)

        val selection = when (link.periodicity) {
            5 -> 0
            30 -> 1
            60 -> 2
            else -> 0
        }
        periodicitySpinner.setSelection(selection)
    }

    override fun onSuccess() {
        FragmentNavigator(activity!!.supportFragmentManager).navigateBack(activity!!)
    }

    override fun onFail() {

    }
}