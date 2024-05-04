package com.example.onebank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper()
        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextTextPassword)
        btnLogin = findViewById(R.id.button)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val isLoginSuccessful = dbHelper.checkUserCredentials(email, password)
                if (isLoginSuccessful) {
                    val intent = Intent(this@MainActivity, AccountInfoActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                } else {
                    // handle login failure
                }
            }
        }

        val button2 = findViewById<Button>(R.id.button2)

        button2.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}