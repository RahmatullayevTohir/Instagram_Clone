package com.example.instagram_clone.utils

import android.app.Application
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import com.example.instagram_clone.model.ScreenSize

object Utils {

    fun fireToast(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

    fun screenSize(context:Application):ScreenSize{
        val displayMetrics =DisplayMetrics()
        val windoManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        windoManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels
        val deciceHight = displayMetrics.widthPixels
        return ScreenSize(deviceWidth,deciceHight)
    }
}