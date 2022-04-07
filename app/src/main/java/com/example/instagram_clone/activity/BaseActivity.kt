package com.example.instagram_clone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagram_clone.R

//  BaseActivity is parent for all Activities

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}