package com.example.montraproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString()
            when {
                email.isEmpty() -> etEmail.error = "Enter your email"
                pass.isEmpty() -> etPassword.error = "Enter your password"
                else -> {
                    // Demo login: any email + password works.
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        findViewById<TextView>(R.id.tvGoSignUp).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        findViewById<TextView>(R.id.tvForgot).setOnClickListener {
            Toast.makeText(this, "Password reset coming soon", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnGoogle).setOnClickListener {
            Toast.makeText(this, "Google sign-in coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}