package com.example.instagram_clone.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagram_clone.R
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.PrefsManager
import com.example.instagram_clone.manager.handler.AuthHandler
import com.example.instagram_clone.manager.handler.DBUserHandler
import com.example.instagram_clone.model.User
import com.example.instagram_clone.utils.Extension.toast
import com.example.instagram_clone.utils.Utils
import kotlin.Exception

/**
 * In SignUpActivity, user can signup using fullname, email, password
 */


class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_email: EditText
    lateinit var et_cpassword: EditText
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    private fun initViews() {
        context = this

        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_cpassword)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener {
            val fullname = et_fullname.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (fullname.isNotEmpty()&& email.isNotEmpty()&& password.isNotEmpty()){
                val user = User(fullname, email, password,"")
                firebaseSignUp(user)
            }
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { finish() }
    }

    private fun firebaseSignUp(user: User){
        showLoading(this)
        AuthManager.signUp(user.email, user.password, object :AuthHandler{
            override fun onSuccess(uid: String) {
                user.uid = uid
                storeUserToDb(user)
                toast(getString(R.string.str_signup_success))
            }

            override fun onError(exception: Exception?) {
//                dismissLoading()
                toast(getString(R.string.str_signup_failed))
            }
        })
    }

    private fun storeUserToDb(user: User){
        user.device_id = Utils.getDeviceID(this)
        user.device_token = PrefsManager(this).loadDeviceToken()!!

        DatabaseManager.storeUser(user, object :DBUserHandler{
            override fun onSuccess(user: User?) {
                dismissLoading()
                callMainActivity(context)
            }

            override fun onError(e: Exception) {

            }
        })
    }
}