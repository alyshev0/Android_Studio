package com.example.alyshevyaminovapp.domain.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alyshevyaminovapp.R
import com.example.alyshevyaminovapp.domain.Models.Item
import com.google.android.material.imageview.ShapeableImageView

class CartAdapter(
    private var items: List<Item>,
    private val onHeaderImageClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit,
    private val onIncreaseQuantity: (Item) -> Unit,
    private val onDecreaseQuantity: (Item) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_card, parent, false)
        return CartViewHolder(view, onHeaderImageClick, onDeleteClick, onIncreaseQuantity, onDecreaseQuantity)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

class CartViewHolder(
    itemView: View,
    private val onHeaderImageClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit,
    private val onIncreaseQuantity: (Item) -> Unit,
    private val onDecreaseQuantity: (Item) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val price: TextView = itemView.findViewById(R.id.subhead)
    private val headerImage: ShapeableImageView = itemView.findViewById(R.id.header_image)
    private val deleteButton: ShapeableImageView = itemView.findViewById(R.id.delete_from_cart)
    private val quantityText: TextView = itemView.findViewById(R.id.text_quantity)
    private val increaseButton: ShapeableImageView = itemView.findViewById(R.id.button_increase)
    private val decreaseButton: ShapeableImageView = itemView.findViewById(R.id.button_decrease)

    fun bind(item: Item) {
        title.text = item.title
        price.text = item.price
        headerImage.setImageResource(item.imageRestID)
        quantityText.text = item.quantity.toString()

        // Обработчик клика на изображение
        headerImage.setOnClickListener {
            onHeaderImageClick(item)
        }

        deleteButton.setOnClickListener {
            onDeleteClick(item)
        }

        // Обработчик клика на кнопку "+"
        increaseButton.setOnClickListener {
            onIncreaseQuantity(item)
        }

        // Обработчик клика на кнопку "-"
        decreaseButton.setOnClickListener {
            onDecreaseQuantity(item)
        }
    }
}}

