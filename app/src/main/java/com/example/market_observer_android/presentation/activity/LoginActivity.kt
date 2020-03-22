package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import com.example.market_observer_android.R
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.presentation.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_login)
        initViews()
    }

    private fun initViews() {
        btn_login.setOnClickListener {
            login()
        }
        btn_register.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        presenter.register(CredentialsEntity(email, password))
    }

    private fun login() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        presenter.login(CredentialsEntity(email, password))
    }
}
