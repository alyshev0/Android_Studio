package com.example.alyshevyaminovapp.domain.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alyshevyaminovapp.R
import com.example.alyshevyaminovapp.domain.Models.Item
import com.google.android.material.imageview.ShapeableImageView

class ItemAdapter(
    private var items: List<Item>,
    private val onAddToCartClick: (Item) -> Unit,
    private val onHeaderImageClick:(Item) -> Unit,
    private val onDetailsClick: (Item) -> Unit) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view, onAddToCartClick,onHeaderImageClick, onDetailsClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
    @Suppress("NotifyDataSetChanged")
    fun updateItems(newItems: List<Item>){
        items = newItems
        notifyDataSetChanged()
    }


    class ItemViewHolder(
        itemView: View,
        private val onAddToCartClick: (Item) -> Unit,
        private val onHeaderImageClick: (Item) -> Unit,
        private val onDetailsClick: (Item) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val subhead: TextView = itemView.findViewById(R.id.subhead)
        private val body: TextView = itemView.findViewById(R.id.body)
        private val headerImage: ShapeableImageView = itemView.findViewById(R.id.header_image)
        private val buttonAddToCart: Button = itemView.findViewById(R.id.button_add_to_cart)
        private val buttonDetails: Button = itemView.findViewById(R.id.button_details)

        fun bind(item: Item) {
            title.text = item.title
            subhead.text = item.price
            body.text = item.description
            headerImage.setImageResource(item.imageRestID)

            buttonAddToCart.setOnClickListener {
                onAddToCartClick(item)
            }

            buttonDetails.setOnClickListener {
                onDetailsClick(item)
            }
            buttonDetails.setOnClickListener{
                onHeaderImageClick(item)
            }
        }
    }
}