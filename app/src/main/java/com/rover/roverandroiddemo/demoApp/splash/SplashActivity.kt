package com.rover.roverandroiddemo.demoApp.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rover.roverandroiddemo.demoApp.dogList.DogListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashActivity, DogListActivity::class.java))
        finish()
    }
}