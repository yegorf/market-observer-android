package com.example.market_observer_android.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class FirebaseService {

    private val tag = FirebaseService::class.java.simpleName

    fun signUp(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(tag, "$email success signUp")
                } else {
                    Log.d(tag, "$email fail signUp")
                }
            }
    }

    fun signIn(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(tag, "$email success signIn")
                } else {
                    Log.d(tag, "$email fail signIn")
                }
            }
    }
}