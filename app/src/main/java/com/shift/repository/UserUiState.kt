package com.shift.repository

import com.shift.model.User

data class UserUiState (
    val users: List<User> = emptyList(),
    )