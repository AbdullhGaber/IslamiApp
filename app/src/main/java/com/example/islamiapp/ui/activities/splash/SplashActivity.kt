package com.example.islamiapp.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.islamiapp.R
import com.example.islamiapp.ui.activities.home.HomeActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                Intent(this@SplashActivity , HomeActivity::class.java).also{
                    startActivity(it)
                    finish()
                }
            },
            2000
        )
    }
}