package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import android.widget.Toast
import com.example.market_observer_android.R
import com.example.market_observer_android.presentation.mvp_view.LoginView
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.LoginPresenter
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
            showProgressDialog()
            signIn()
        }
        btn_register.setOnClickListener {
            showProgressDialog()
            signUp()
        }
    }

    private fun signUp() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        presenter.signUp(email, password)
    }

    private fun signIn() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        presenter.signIn(email, password)
    }

    override fun openHomeScreen() {
        ActivityNavigator.navigateToMainActivity(this)
    }

    override fun showErrorPopup(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}
