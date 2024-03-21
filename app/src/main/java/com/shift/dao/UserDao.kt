package com.shift.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.shift.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM  Users")
    fun getAllUsers(): List<UserEntity>

    @Insert()
    fun saveUsers(users: List<UserEntity>)

    @Query("DELETE FROM Users")
    fun deleteAllUsers()
}