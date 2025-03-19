package com.example.alyshevyaminovapp.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alyshevyaminovapp.domain.Models.Item
import com.example.alyshevyaminovapp.presentaion.home.ItemDetails

class SharedViewModel : ViewModel() {
    val selectedItems: MutableLiveData<List<Item>> = MutableLiveData()
    val totalCost = MutableLiveData<String>()

    init {
        totalCost.value = "0 RUB"
    }

    private fun updateTotalCost(items: List<Item>) {

        var total = 0
        for (item in items) {
            val price = item.price.replace(oldValue = " RUB", "").toIntOrNull() ?: 0
            total += price * item.quantity
        }
        totalCost.value = "$total RUB"
    }

    fun addItem(item: Item) {

        val currentList = selectedItems.value ?: mutableListOf()

        val existingItem = currentList.find { it.title == item.title }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(item)
            selectedItems.value = updatedList
        }

        updateTotalCost(selectedItems.value ?: emptyList())
    }


    fun removeItem(item: Item) {

        val currentList = selectedItems.value ?: mutableListOf()
        val updatedlist = currentList.toMutableList()
        updatedlist.remove(item) // Удаляем товар из списка

        selectedItems.value = updatedlist

        updateTotalCost(updatedlist)

    }

    fun increaseQuantity(item: Item) {

        val currentList = selectedItems.value ?: mutableListOf()

        val existingItem = currentList.find { it.title == item.title }


        if (existingItem != null) {

            existingItem.quantity += 1
            selectedItems.value = currentList
            updateTotalCost(currentList)
        }
    }


    fun decreaseQuantity(item: Item) {

        val currentList = selectedItems.value ?: mutableListOf()
        val existingItem = currentList.find { it.title == item.title }

        if (existingItem != null) {

           if ( existingItem.quantity > 1){
               existingItem.quantity -= 1
           }else{
               removeItem(existingItem)
           }
            selectedItems.value = currentList
            updateTotalCost(currentList)
        }

    }
    fun showDetails(context: Context, item: Item) {

        val intent = Intent(context, ItemDetails::class.java).apply {

            putExtra("item_title", item.title)

            putExtra("item_price", item.price)

            putExtra("item_description", item.description)

            putExtra("item_image", item.imageRestID)
        }
        context.startActivity(intent)

    }




}