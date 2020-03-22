package com.example.market_observer_android.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.market_observer_android.R
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.LoginPresenter
import com.example.market_observer_android.presentation.presenter.LoginPresenterImpl
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val presenter: LoginPresenter = LoginPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
//        val email = et_email.text
//        val password = et_password.text

        //todo presenter -> repo -> api
        ActivityNavigator.navigateToMainActivity(this)
    }

    private fun login() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        presenter.login(CredentialsEntity(email, password))
    }
}
