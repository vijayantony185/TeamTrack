package com.example.teamtrack.arch.userLogIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamtrack.arch.localDB.UserSessionManager
import com.example.teamtrack.arch.userRegister.RegisterRequest
import com.example.teamtrack.arch.userRegister.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    val userSessionManager: UserSessionManager
) : ViewModel() {

    var mLoginResponse = MutableLiveData<Response<RegisterResponse>>()
    val loginResponse: LiveData<Response<RegisterResponse>> = mLoginResponse

    fun loginUser(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                repository.userLogin(request).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            userSessionManager.saveUserDetails(request.email, response.body()?.token)
                        }
                        mLoginResponse.value = response
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}