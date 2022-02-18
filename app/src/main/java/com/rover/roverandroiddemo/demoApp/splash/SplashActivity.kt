package com.rover.roverandroiddemo.demoApp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rover.roverandroiddemo.demoApp.dogList.DogListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this@SplashActivity, DogListActivity::class.java))
            finish()
        }, 2000)
    }
}