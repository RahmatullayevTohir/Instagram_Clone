package com.example.instagram_clone.manager.handler

import com.example.instagram_clone.model.User

interface DBUsersHandler {
    fun onSuccess(users: ArrayList<User>)
    fun onError(e: Exception)
}