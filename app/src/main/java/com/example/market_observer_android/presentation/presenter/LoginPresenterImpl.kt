package com.example.market_observer_android.presentation.presenter

import android.util.Log
import com.example.market_observer_android.common.util.UseCaseObserver
import com.example.market_observer_android.data.entity.SettingsEntity
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.domain.util.PreferenceManager
import com.example.market_observer_android.presentation.mvp_view.LoginView
import io.reactivex.disposables.CompositeDisposable

class LoginPresenterImpl(val repository: Repository) : LoginPresenter,
    BasePresenterImpl<LoginView>() {

    private val tag = LoginPresenterImpl::class.java.simpleName
    private val disposables = CompositeDisposable()

    override fun signUp(email: String, password: String) {
        repository.signUp(email, password)
            .subscribe(getSignUpObserver())
    }

    override fun signIn(email: String, password: String) {
        repository.signIn(email, password)
            .subscribe(getSignInObserver())
    }

    private fun getSignUpObserver(): UseCaseObserver<Boolean> {
        return object : UseCaseObserver<Boolean>() {

            override fun onComplete() {
                super.onComplete()
                view?.openHomeScreen()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view?.showErrorPopup(e.message!!)
            }
        }
    }

    private fun getSignInObserver(): UseCaseObserver<Boolean> {
        return object : UseCaseObserver<Boolean>() {

            override fun onComplete() {
                super.onComplete()
                disposables.add(
                    repository.getSettings()
                        .subscribe({ setSettings(it) }, { Log.d(tag, it.message!!) })
                )
                view?.openHomeScreen()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view?.showErrorPopup(e.message!!)
            }
        }
    }

    private fun setSettings(settings: SettingsEntity) {
        PreferenceManager.setNotificationsOn(settings.appNotificationsOn)
        PreferenceManager.setEmailNotificationsOn(settings.emailNotificationsOn)
        PreferenceManager.setObserveNewLink(settings.observeNewLinkOn)
        PreferenceManager.setStoreRemote(settings.autoBackupOn)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}