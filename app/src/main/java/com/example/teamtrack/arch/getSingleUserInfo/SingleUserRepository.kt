package com.example.teamtrack.arch.getSingleUserInfo

import com.example.teamtrack.arch.SingleUserDetails
import com.example.teamtrack.network.ApiService
import retrofit2.Call
import javax.inject.Inject

class SingleUserRepository @Inject constructor(private val apiService: ApiService) {

    fun getSingleUserDetails(userId : Int) : Call<SingleUserDetails>{
        return apiService.getSingleUser(userId)
    }
}