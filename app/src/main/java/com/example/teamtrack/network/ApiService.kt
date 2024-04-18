package com.example.teamtrack.network

import com.example.teamtrack.arch.SingleUserDetails
import com.example.teamtrack.arch.UserDetailsResponse
import com.example.teamtrack.arch.userRegister.RegisterRequest
import com.example.teamtrack.arch.userRegister.RegisterResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/users?")
    fun getUsers(@Query("page") page : Int,   @Query("per_page") perPage: Int) : Call<UserDetailsResponse>

    @GET("api/users/{userId}")
    fun getSingleUser(@Path("userId") userId : Int): Call<SingleUserDetails>

    @POST("api/register")
    fun registerUser(@Body userRegisterRequest: RegisterRequest) : Call<RegisterResponse>

    @POST("api/login")
    fun loginUser(@Body userRegisterRequest: RegisterRequest) : Call<RegisterResponse>
}