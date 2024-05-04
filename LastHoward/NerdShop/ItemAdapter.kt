package com.example.orderone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(private val items: List<Item>, private val onItemClick: (Item) -> Unit) : RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
    private val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)

    fun bind(item: Item) {
        itemNameTextView.text = item.name
        itemPriceTextView.text = "$${item.price}"
    }
}


