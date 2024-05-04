package com.example.orderone

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val items = listOf(
        Item("Desk", 100.0),
        Item("Gaming Chair", 200.0),
        Item("Keyboard", 50.0),
        Item("Mouse", 30.0),
        Item("LED Lights", 20.0)
    )

    private val cart = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.itemsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(items) { item ->
            addToCart(item)
        }

        val checkoutButton: Button = findViewById(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            val totalCost = calculateTotalCost()
            Toast.makeText(this, "Total cost: $$totalCost", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToCart(item: Item) {
        cart.add(item)
    }

    private fun calculateTotalCost(): Double {
        var total = 0.0
        for (item in cart) {
            total += item.price
        }
        return total
    }
}
