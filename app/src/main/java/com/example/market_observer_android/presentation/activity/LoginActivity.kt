package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import android.widget.Button
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.mvp_view.LoginView
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.LoginPresenter
import com.example.market_observer_android.presentation.util.showLongToast
import com.example.market_observer_android.presentation.util.showShortToast
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        component.inject(this)
        presenter.onCreate(this)
        initViews()
    }

    private fun initViews() {
        btn_login.setOnClickListener {
            signIn()
        }
        btn_register.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = et_email.getText()
        val password = et_password.getText()

        if (validateInputs(email, password)) {
            showProgressDialog()
            presenter.signUp(email, password)
        } else {
            showShortToast(R.string.invalid_data)
        }
    }

    private fun signIn() {
        val email = et_email.getText()
        val password = et_password.getText()

        if (validateInputs(email, password)) {
            showProgressDialog()
            presenter.signIn(email, password)
        } else {
            showShortToast(R.string.invalid_data)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    override fun openHomeScreen() {
        ActivityNavigator.navigateToMainActivity(this)
    }

    override fun showErrorPopup(error: String) {
        dismissProgressDialog()
        showLongToast(error)
    }
}
