package com.example.market_observer_android.presentation.util

import android.widget.Toast
import androidx.annotation.StringRes
import com.example.market_observer_android.common.application.Application

fun showShortToast(text: String) {
    Toast.makeText(Application.getInstance(), text, Toast.LENGTH_SHORT).show()
}

fun showLongToast(text: String) {
    Toast.makeText(Application.getInstance(), text, Toast.LENGTH_LONG).show()
}

fun showShortToast(@StringRes textRes: Int) {
    Toast.makeText(Application.getInstance(), textRes, Toast.LENGTH_SHORT).show()
}

fun showLongToast(@StringRes textRes: Int) {
    Toast.makeText(Application.getInstance(), textRes, Toast.LENGTH_LONG).show()
}
