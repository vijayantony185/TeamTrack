package com.example.teamtrack.arch.getAllUsers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.teamtrack.arch.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val userDetailsRepository: UserDetailsRepository) :
    ViewModel() {

    fun getUsers(): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6),
            pagingSourceFactory = { userDetailsRepository.getPagingUserData() }
        ).flow.cachedIn(viewModelScope)
    }
}