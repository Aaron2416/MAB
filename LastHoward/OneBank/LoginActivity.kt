package com.example.onebank

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        dbHelper = DatabaseHelper()

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonSignUp)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val isValidUser = dbHelper.checkUserCredentials(email, password)
                    withContext(Dispatchers.Main) {
                        if (isValidUser) {
                            Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}