package com.example.market_observer_android.presentation.presenter

import com.example.market_observer_android.presentation.mvp_view.MyAccountView
import com.google.firebase.auth.FirebaseAuth

class MyAccountPresenterImpl : MyAccountPresenter, BasePresenterImpl<MyAccountView>() {

    override fun onCreate(view: MyAccountView) {
        super.onCreate(view)
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            view.setData(email)
        }
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
        view?.openLoginScreen()
    }
}