package com.example.market_observer_android.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.market_observer_android.data.injection.DataModule
import com.example.market_observer_android.presentation.injection.DaggerPresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationComponent
import com.example.market_observer_android.presentation.injection.PresentationModule
import com.example.market_observer_android.presentation.popup.ProgressPopup

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
        ProgressPopup().show(supportFragmentManager, ProgressPopup::class.java.simpleName)
    }

    protected fun dismissProgressDialog() {
        val dialog = supportFragmentManager.findFragmentByTag(ProgressPopup::class.java.simpleName)
        if (dialog != null) {
            if (dialog is DialogFragment) {
                dialog.dismiss()
            }
            supportFragmentManager.beginTransaction().remove(dialog)
        }
    }
}