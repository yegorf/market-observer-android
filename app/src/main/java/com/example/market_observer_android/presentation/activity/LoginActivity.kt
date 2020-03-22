package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.market_observer_android.R
import com.example.market_observer_android.data.entity.CredentialsEntity
import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.presentation.injection.DaggerPresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationModule
import com.example.market_observer_android.presentation.navigation.ActivityNavigator
import com.example.market_observer_android.presentation.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_login)
        initViews()
    }

    private fun inject() {
        DaggerPresentationComponent.builder()
            .dataModule(DataModule())
            .presentationModule(PresentationModule())
            .build()
            .inject(this)
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
