package com.shift.repository

import com.shift.dao.UserDao
import com.shift.entity.UserEntity

class SQLiteUserRepository(private val userDao: UserDao) {

    fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }

    fun saveUsers(users: List<UserEntity>){
        userDao.saveUsers(users)
    }

    fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}