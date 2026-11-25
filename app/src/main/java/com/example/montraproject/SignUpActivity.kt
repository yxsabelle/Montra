package com.example.montraproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirm)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        findViewById<Button>(R.id.btnCreate).setOnClickListener {
            val pass = etPassword.text.toString()
            when {
                etName.text.toString().trim().isEmpty() -> etName.error = "Enter your name"
                etEmail.text.toString().trim().isEmpty() -> etEmail.error = "Enter your email"
                pass.length < 6 -> etPassword.error = "At least 6 characters"
                pass != etConfirm.text.toString() -> etConfirm.error = "Passwords do not match"
                !cbTerms.isChecked ->
                    Toast.makeText(this, "Please agree to the Terms", Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }

        findViewById<TextView>(R.id.tvGoLogin).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnGoogle).setOnClickListener {
            Toast.makeText(this, "Google sign-in coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}