package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.presentation.fragment.ProgressDialog
import com.example.market_observer_android.presentation.injection.DaggerPresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationModule

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var component: PresentationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerPresentationComponent.builder()
            .dataModule(DataModule())
            .presentationModule(PresentationModule())
            .build()
    }

    protected fun showProgressDialog() {
        ProgressDialog().show(supportFragmentManager, ProgressDialog::class.java.simpleName)
    }

    protected fun dismissProgressDialog() {
        val dialog = supportFragmentManager.findFragmentByTag(ProgressDialog::class.java.simpleName)
        if (dialog != null) {
            supportFragmentManager.beginTransaction().remove(dialog)
        }
    }
}