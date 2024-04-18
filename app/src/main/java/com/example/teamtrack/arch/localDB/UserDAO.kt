package com.example.teamtrack.arch.localDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teamtrack.arch.Data

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Data)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<Data>>

    @Delete
    fun delete(user: Data)
}