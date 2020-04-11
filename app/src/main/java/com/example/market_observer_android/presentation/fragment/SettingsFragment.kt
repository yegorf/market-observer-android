package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.mvp_view.SettingsView
import com.example.market_observer_android.presentation.presenter.SettingsPresenter
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsView {

    @Inject
    lateinit var presenter: SettingsPresenter

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getComponent().inject(this)
        presenter.onCreate(this)
        initSwitches()
    }

    private fun initSwitches() {
        switch_notifications.isChecked = PreferenceManager.isNotificationsOn()
        switch_notifications.setOnCheckedChangeListener { buttonView, isChecked ->
            PreferenceManager.setNotificationsOn(isChecked)
        }

        switch_email.isChecked = PreferenceManager.isEmailNotificationsOn()
        switch_email.setOnCheckedChangeListener { buttonView, isChecked ->
            PreferenceManager.setEmailNotificationsOn(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}