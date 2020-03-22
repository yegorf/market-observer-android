package com.example.market_observer_android.data.entity

import com.google.gson.annotations.SerializedName

data class CredentialsEntity(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)