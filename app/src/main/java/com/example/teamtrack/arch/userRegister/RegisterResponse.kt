package com.example.teamtrack.arch.userRegister

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("token") var token: String? = null,
    var status : Boolean? = false,
    var message : String? = null
)

data class RegisterRequest(
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null
)