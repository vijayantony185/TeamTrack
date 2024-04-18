package com.example.teamtrack.arch.localDB

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideUserDataBase(@ApplicationContext context : Context) : UserDataBase {
        return Room.databaseBuilder(context, UserDataBase::class.java, "userDB").build()
    }

    @Provides
    fun provideUserDao(userDataBase: UserDataBase) : UserDAO {
        return userDataBase.userDao()
    }
}