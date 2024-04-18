package com.example.teamtrack.arch.getAllUsers

import com.example.teamtrack.arch.localDB.UserDAO
import com.example.teamtrack.network.ApiService
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(private val apiService: ApiService, private val userDAO: UserDAO) {

    fun getPagingUserData() : UserPagingSource {
        return UserPagingSource(apiService, userDAO)
    }
}