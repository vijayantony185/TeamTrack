package com.example.teamtrack.arch.getSingleUserInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamtrack.arch.Data
import com.example.teamtrack.arch.SingleUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SingleUserViewModel @Inject constructor(private val singleUserRepository: SingleUserRepository) :
    ViewModel() {

    var mUserData = MutableLiveData<Data>()
    val userData: LiveData<Data> = mUserData

    fun getSingleUser(userId: Int) {
        viewModelScope.launch {
            try {
                singleUserRepository.getSingleUserDetails(userId)
                    .enqueue(object : Callback<SingleUserDetails> {
                        override fun onResponse(
                            call: Call<SingleUserDetails>,
                            response: Response<SingleUserDetails>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.data?.let { data ->
                                    mUserData.value = data
                                }
                            }
                        }

                        override fun onFailure(call: Call<SingleUserDetails>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}