package com.example.instagram_clone.manager.handler

import com.example.instagram_clone.model.Post
import com.example.instagram_clone.model.User

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}