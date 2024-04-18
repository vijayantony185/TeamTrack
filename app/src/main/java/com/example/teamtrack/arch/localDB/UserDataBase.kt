package com.example.teamtrack.arch.localDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teamtrack.arch.Data

@Database(entities = [Data::class], exportSchema = false, version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao() : UserDAO
}