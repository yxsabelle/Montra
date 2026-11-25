package com.example.montraproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val seen = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getBoolean("onboarding_done", false)
            val next = if (seen) LoginActivity::class.java else OnboardingActivity::class.java
            startActivity(Intent(this, next))
            finish()
        }, 2500)
    }
}