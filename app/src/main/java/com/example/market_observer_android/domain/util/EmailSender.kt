package com.example.market_observer_android.domain.util

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.market_observer_android.BuildConfig

object EmailSender {

    private val tag = EmailSender::class.java.simpleName

    fun contactUs(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yegorkhohlov@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Web Eye")
            context.startActivity(Intent.createChooser(intent, "Send mail with"))
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, e.message)
            }
        }
    }
}