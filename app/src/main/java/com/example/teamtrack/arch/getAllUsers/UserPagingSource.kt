package com.example.teamtrack.arch.getAllUsers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.teamtrack.arch.Data
import com.example.teamtrack.arch.UserDetailsResponse
import com.example.teamtrack.arch.localDB.UserDAO
import com.example.teamtrack.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserPagingSource(private val apiService: ApiService, private val userDAO: UserDAO) :
    PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val pageNumber = params.key ?: 1

            val userDetailsResponse = suspendCoroutine { continuation ->
                apiService.getUsers(pageNumber, params.loadSize)
                    .enqueue(object : Callback<UserDetailsResponse> {
                        override fun onResponse(
                            call: Call<UserDetailsResponse>,
                            response: Response<UserDetailsResponse>
                        ) {
                            response.body()?.let {
                                continuation.resume(it)
                            } ?: run {
                                continuation.resumeWithException(NullPointerException("Response body is null"))
                            }
                        }

                        override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                            continuation.resumeWithException(t)
                        }

                    })
            }

            userDetailsResponse.data.let {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        it.forEach { data ->
                            data.country = Data.getRandomCountry()
                            userDAO.insert(data)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            val prevPage = if (pageNumber > 1) pageNumber - 1 else null
            val nextPage =
                if (userDetailsResponse.totalPages != null && pageNumber < userDetailsResponse.totalPages!!) pageNumber + 1 else null

            LoadResult.Page(
                data = userDetailsResponse.data,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}

