package com.example.onebank

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singup)

        dbHelper = DatabaseHelper()

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    dbHelper.addUserToDatabase(email, password)
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, "User added successfully", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@SignUpActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
