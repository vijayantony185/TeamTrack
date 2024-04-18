package com.example.teamtrack.arch.userLogIn

import com.example.teamtrack.arch.userRegister.RegisterRequest
import com.example.teamtrack.arch.userRegister.RegisterResponse
import com.example.teamtrack.network.ApiService
import retrofit2.Call
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {

    fun userLogin(loginRequest: RegisterRequest) : Call<RegisterResponse> {
        return apiService.loginUser(loginRequest)
    }
}