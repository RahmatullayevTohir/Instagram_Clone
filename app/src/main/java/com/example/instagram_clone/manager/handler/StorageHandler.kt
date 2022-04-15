package com.example.instagram_clone.manager.handler

import java.lang.Exception

interface StorageHandler {
    fun onSuccess(imgUrl:String)
    fun onError(exception: Exception)
}