package com.example.market_observer_android.presentation.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.market_observer_android.R
import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.mvp_view.SettingsView
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.SettingsPresenter
import kotlinx.android.synthetic.main.fragment_settings.view.*
import javax.inject.Inject


class SettingsFragment : BaseFragment(), SettingsView {

    @Inject
    lateinit var presenter: SettingsPresenter

    private lateinit var notificationsSwitch: Switch
    private lateinit var emailSwitch: Switch
    private lateinit var observeSwitch: Switch
    private lateinit var dataStorageSwitch: Switch
    private lateinit var emailTextView: TextView
    private lateinit var signOutButton: TextView
    private lateinit var uploadDataButton: LinearLayout
    private lateinit var progressBar: ProgressBar

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
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        getComponent().inject(this)
        initViews(view)
        initSwitches()
        presenter.onCreate(this)
        return view
    }

    private fun initViews(view: View) {
        notificationsSwitch = view.switch_notifications
        emailSwitch = view.switch_email
        observeSwitch = view.switch_observe_links
        dataStorageSwitch = view.switch_data_storage
        emailTextView = view.tv_email
        signOutButton = view.btn_sign_out
        uploadDataButton = view.btn_upload_cloud
        progressBar = view.download_progress_bar
    }

    private fun initSwitches() {
        notificationsSwitch.isChecked = PreferenceManager.isNotificationsOn()
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setNotificationsOn(isChecked)
            saveSettings()
        }

        emailSwitch.isChecked = PreferenceManager.isEmailNotificationsOn()
        emailSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setEmailNotificationsOn(isChecked)
            saveSettings()
        }

        observeSwitch.isChecked = PreferenceManager.isObserveNewLink()
        observeSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setObserveNewLink(isChecked)
            saveSettings()
        }

        dataStorageSwitch.isChecked = PreferenceManager.isStoreRemote()
        dataStorageSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.setStoreRemote(isChecked)
            saveSettings()
        }

        uploadDataButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            presenter.uploadCloud()
        }
    }

    private fun saveSettings() {
        val settings = SettingsEntity(
            PreferenceManager.isNotificationsOn(),
            PreferenceManager.isEmailNotificationsOn(),
            PreferenceManager.isObserveNewLink(),
            PreferenceManager.isStoreRemote()
        )
        presenter.saveSettings(settings)
    }

    override fun setUserData(email: String) {
        emailTextView.text = email
        signOutButton.setOnClickListener {
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

    override fun onDownloadFinish() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun hasNavigationArrow() = false
}