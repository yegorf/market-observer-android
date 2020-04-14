package com.example.market_observer_android.presentation.presenter

import android.util.Log
import com.example.market_observer_android.common.util.UseCaseObserver
import com.example.market_observer_android.data.repository.Repository
import com.example.market_observer_android.presentation.mvp_view.LoginView
import com.google.firebase.auth.FirebaseAuth

class LoginPresenterImpl(val repository: Repository) : LoginPresenter,
    BasePresenterImpl<LoginView>() {

    private val tag = LoginPresenterImpl::class.java.simpleName

    override fun onCreate(view: LoginView) {
        super.onCreate(view)
        if (FirebaseAuth.getInstance().currentUser != null) {
            view.openHomeScreen()
        }
    }

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
                Log.d(tag, "success")
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                view?.showErrorPopup(e.message!!)
                Log.d(tag, "error: ${e.message}")
            }
        }
    }

    private fun getSignInObserver(): UseCaseObserver<Boolean> {
        return object : UseCaseObserver<Boolean>() {

            override fun onComplete() {
                super.onComplete()
                Log.d(tag, "success")
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                Log.d(tag, "error: ${e.message}")
            }
        }
    }
}