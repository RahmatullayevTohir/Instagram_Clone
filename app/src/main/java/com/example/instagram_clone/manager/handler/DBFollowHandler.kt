package com.example.instagram_clone.manager.handler

import com.example.instagram_clone.model.User

interface DBFollowHandler {
    fun onSuccess(isDone: Boolean)
    fun onError(e: Exception)
}