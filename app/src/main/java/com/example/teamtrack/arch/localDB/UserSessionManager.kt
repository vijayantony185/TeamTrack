package com.example.teamtrack.arch.localDB

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionManager @Inject constructor(@ApplicationContext context : Context) {
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_AUTH_TOKEN = "authToken"
    }

    fun saveUserDetails(username : String?, authToken : String?) {
     sharedPreferences.edit().apply {
            putString(KEY_USERNAME, username)
            putString(KEY_AUTH_TOKEN, authToken)
            apply()
        }
    }

    private fun getUsername() : String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    private fun getAuthToken() : String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearUserSession() {
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
    }

    fun isUserLoggedIn() : Boolean{
        return getUsername() != null && getAuthToken() != null
    }
}