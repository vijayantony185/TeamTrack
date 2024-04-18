package com.example.teamtrack.arch.userRegister

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val registerRepository: RegisterRepository) :
    ViewModel() {

    var mRegisterResponse = MutableLiveData<Response<RegisterResponse>>()
    val RegisterResponse: LiveData<Response<RegisterResponse>> = mRegisterResponse

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                registerRepository.registerUser(request)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            mRegisterResponse.value = response
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