package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.mvp_view.LoginView
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.LoginPresenter
import com.example.market_observer_android.presentation.util.showLongToast
import com.example.market_observer_android.presentation.util.showShortToast
import com.example.market_observer_android.presentation.view.StyledButton
import com.example.market_observer_android.presentation.view.StyledEditText
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    lateinit var emailInput: StyledEditText
    lateinit var passwordInput: StyledEditText
    lateinit var loginButton: StyledButton
    lateinit var registerButton: StyledButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        component.inject(this)
        presenter.onCreate(this)
        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        emailInput = login_email_input
        passwordInput = login_password_input
        loginButton = login_button
        registerButton = register_button
    }

    private fun setOnClickListeners() {
        loginButton.setOnClickListener {
            signIn()
        }
        registerButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = emailInput.getText()
        val password = passwordInput.getText()

        if (validateInputs(email, password)) {
            if (validatePassword(password)) {
                showProgressDialog()
                presenter.signUp(email, password)
            } else {
                showLongToast(getString(R.string.digit_warning))
            }
        } else {
            showShortToast(R.string.invalid_data)
        }
    }

    private fun signIn() {
        val email = emailInput.getText()
        val password = passwordInput.getText()

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

    private fun validatePassword(password: String): Boolean {
        return password.any {
            it.isDigit()
        }
    }

    override fun openHomeScreen() {
        ActivityNavigator.navigateToMainActivity(this)
    }

    override fun showErrorPopup(error: String) {
        dismissProgressDialog()
        showLongToast(error)
    }
}
