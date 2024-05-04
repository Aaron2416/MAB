package com.example.onebank
import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class DatabaseHelper {
    private val client = OkHttpClient()

    suspend fun addUserToDatabase(email: String, password: String) {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/users")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("DatabaseHelper", "Unexpected response code: ${response.code}")
                throw IOException("Unexpected code $response")
            }
        }
    }

    suspend fun checkUserCredentials(email: String, password: String): Boolean {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/users/authenticate")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("DatabaseHelper", "Unexpected response code: ${response.code}")
                throw IOException("Unexpected code $response")
            }
            val responseBody = response.body?.string()
            Log.d("DatabaseHelper", "Response body: $responseBody")
            return responseBody?.contains("Login successful") == true
        }
    }

    suspend fun transferMoney(senderEmail: String, recipientEmail: String, amount: Double): Boolean {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("senderEmail", senderEmail)
            .add("recipientEmail", recipientEmail)
            .add("amount", amount.toString())
            .build()

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/users/transfer")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("DatabaseHelper", "Unexpected response code: ${response.code}")
                throw IOException("Unexpected code $response")
            }
            val responseBody = response.body?.string()
            Log.d("DatabaseHelper", "Response body: $responseBody")
            return responseBody?.contains("Transfer successful") == true
        }
    }

    suspend fun fetchUserBalance(email: String): Double {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("email", email)
            .build()

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/users/balance")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("DatabaseHelper", "Unexpected response code: ${response.code}")
                throw IOException("Unexpected code $response")
            }
            val responseBody = response.body?.string()
            Log.d("DatabaseHelper", "Response body: $responseBody")
            val jsonObject = JSONObject(responseBody)
            return jsonObject.getDouble("balance")
        }
    }
}