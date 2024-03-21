package com.shift.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.shift.model.User
import com.shift.model.UserResponse


interface RandomUserApi {
    @GET("api/")
    fun getRandomUser(@Query("results") numberOfUsers: Int): Call<UserResponse>
}

