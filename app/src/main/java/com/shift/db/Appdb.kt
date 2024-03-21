package com.shift.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shift.dao.UserDao
import com.shift.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 4,
)

abstract class Appdb: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: Appdb? = null

        fun getInstance(contex: Context): Appdb{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(contex.applicationContext,
                    Appdb::class.java, "app_db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}