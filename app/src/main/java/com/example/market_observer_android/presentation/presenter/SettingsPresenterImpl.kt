package com.example.market_observer_android.presentation.presenter

import android.util.Log
import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.data.util.RemoteDownloadManager
import com.example.market_observer_android.presentation.mvp_view.SettingsView
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable

class SettingsPresenterImpl(
    private val repository: Repository,
    private val backupManager: RemoteDownloadManager
) : SettingsPresenter,
    BasePresenterImpl<SettingsView>() {

    private val tag = SettingsPresenterImpl::class.java.simpleName
    private val disposables = CompositeDisposable()

    override fun onCreate(view: SettingsView) {
        super.onCreate(view)
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            view.setUserData(email)
        }
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
        view?.openLoginScreen()
    }

    override fun saveSettings(settings: SettingsEntity) {
        repository.saveSettings(settings)
    }

    override fun uploadCloud() {
        //fixme
        val subscribe = backupManager.downloadRemote()
            .subscribe {
                Log.d(tag, it)
                view?.onDownloadFinish()
            }
        disposables.add(subscribe)
    }

    override fun onRelease() {
        disposables.clear()
    }
}