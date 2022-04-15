package com.example.instagram_clone.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.instagram_clone.R
import com.example.instagram_clone.manager.AuthManager

/*
// In SplashActivity, user can visit to SignInActivity or MainActivity
*/

class SplashActivity : BaseActivity() {
    val TAG = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
        countDownTimer()
    }

    private fun countDownTimer() {
        object :CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (AuthManager.isSignedIn()){
                    callMainActivity(this@SplashActivity)
                }else{
                    callSignInActivity(this@SplashActivity)
                }
            }
        }.start()
    }
}