package com.example.onebank

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AccountInfoActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tvCurrentBalance: TextView
    private lateinit var etEmail: EditText
    private lateinit var etAmount: EditText
    private lateinit var btnTransferMoney: Button
    private lateinit var tvTransactionDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        dbHelper = DatabaseHelper()
        tvCurrentBalance = findViewById(R.id.tv_current_balance)
        etEmail = findViewById(R.id.et_email)
        etAmount = findViewById(R.id.et_amount)
        btnTransferMoney = findViewById(R.id.btn_transfer_money)
        tvTransactionDate = findViewById(R.id.tv_transaction_date)

        val email = intent.getStringExtra("email")

        CoroutineScope(Dispatchers.IO).launch {
            val balance = dbHelper.fetchUserBalance(email ?: "")
            runOnUiThread {
                tvCurrentBalance.text = "Current Balance: $balance"
            }
        }

        btnTransferMoney.setOnClickListener {
            val recipientEmail = etEmail.text.toString()
            val amountToTransfer = etAmount.text.toString().toDouble()
            CoroutineScope(Dispatchers.IO).launch {
                val balance = dbHelper.fetchUserBalance(email ?: "").toDouble()
                if (amountToTransfer > balance) {
                    runOnUiThread {
                        Toast.makeText(this@AccountInfoActivity, "Insufficient balance", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val isTransferSuccessful = dbHelper.transferMoney(email ?: "", recipientEmail, amountToTransfer)
                    if (isTransferSuccessful) {
                        runOnUiThread {
                            val sdf = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss", Locale.getDefault())
                            val currentDate = sdf.format(Date())
                            tvTransactionDate.text = "Transaction Date: $currentDate"
                        }
                    } else {
                        runOnUiThread {
                            tvTransactionDate.text = "Transaction failed"
                        }
                    }
                }
            }
        }
    }
}