package com.example.alyshevyaminovapp.domain.Services

import com.example.alyshevyaminovapp.domain.Models.Item

class Filter(private val items: List<Item>) {

    fun filterByPrice(maxPrice: Int): List<Item> {
        return items.filter {
            val price = it.price.replace(" RUB", "").toIntOrNull() ?: 0
            price <= maxPrice
        }
    }

    fun filterByCategory(keyword: String): List<Item> {
        return items.filter {
            it.title.contains(keyword, ignoreCase = true)
        }
    }

    // Сброс фильтров (возвращает оригинальный список)
    fun resetFilter(): List<Item> {
        return items
    }
}