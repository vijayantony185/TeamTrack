package com.example.teamtrack.arch.userRegister

import com.example.teamtrack.network.ApiService
import retrofit2.Call
import javax.inject.Inject

class RegisterRepository @Inject constructor(val apiService: ApiService) {

    fun registerUser(userRegisterRequest: RegisterRequest) : Call<RegisterResponse> {
        return apiService.registerUser(userRegisterRequest)
    }
}