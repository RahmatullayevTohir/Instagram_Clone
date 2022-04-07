package com.example.instagram_clone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagram_clone.R

/**
 * In SignUpActivity, user can signup using fullname, email, password
 */


class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_email: EditText
    lateinit var et_cpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_cpassword)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener { finish() }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { finish() }
    }
}