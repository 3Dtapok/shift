package com.shift.model

class UserService {
    private val users: MutableList<User> = mutableListOf()

    fun getUsers(): List<User> = users

    fun addUser(user: User){
        users.add(user)
    }

    fun addUsers(newusers: List<User>){
        users.addAll(newusers)
    }

    fun removeUser(user: User){
        users.remove(user);
    }

    fun clearUsers(){
        users.clear()
    }
}