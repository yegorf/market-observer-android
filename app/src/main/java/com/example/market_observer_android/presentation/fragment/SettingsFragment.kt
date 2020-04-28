package com.example.market_observer_android.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_observer_android.R
import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.mvp_view.SettingsView
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.SettingsPresenter
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
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
        getComponent().inject(this)
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        initSwitches(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onCreate(this)
    }

    private fun initSwitches(view: View) {
        view.switch_notifications.isChecked = PreferenceManager.isNotificationsOn()
        view.switch_notifications.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setNotificationsOn(isChecked)
            val settings = SettingsEntity(
                PreferenceManager.isNotificationsOn(),
                PreferenceManager.isEmailNotificationsOn(),
                PreferenceManager.isObserveNewLink(),
                PreferenceManager.isStoreRemote()
            )
            presenter.saveSettings(settings)
        }

        view.switch_email.isChecked = PreferenceManager.isEmailNotificationsOn()
        view.switch_email.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setEmailNotificationsOn(isChecked)
            val settings = SettingsEntity(
                PreferenceManager.isNotificationsOn(),
                PreferenceManager.isEmailNotificationsOn(),
                PreferenceManager.isObserveNewLink(),
                PreferenceManager.isStoreRemote()
            )
            presenter.saveSettings(settings)
        }

        view.switch_observe_links.isChecked = PreferenceManager.isObserveNewLink()
        view.switch_observe_links.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setObserveNewLink(isChecked)
            val settings = SettingsEntity(
                PreferenceManager.isNotificationsOn(),
                PreferenceManager.isEmailNotificationsOn(),
                PreferenceManager.isObserveNewLink(),
                PreferenceManager.isStoreRemote()
            )
            presenter.saveSettings(settings)
        }

        view.switch_data_storage.isChecked = PreferenceManager.isStoreRemote()
        view.switch_data_storage.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setStoreRemote(isChecked)
            val settings = SettingsEntity(
                PreferenceManager.isNotificationsOn(),
                PreferenceManager.isEmailNotificationsOn(),
                PreferenceManager.isObserveNewLink(),
                PreferenceManager.isStoreRemote()
            )
            presenter.saveSettings(settings)
        }
    }

    override fun setUserData(email: String) {
        tv_email.text = email
        btn_sign_out.setOnClickListener {
            presenter.signOut()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onDestroy()
    }

    override fun openLoginScreen() {
        ActivityNavigator.navigateToLoginActivity(activity!!)
    }

    override fun hasNavigationArrow() = false
}