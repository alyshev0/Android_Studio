package com.example.alyshevyaminovapp.domain.Models

import androidx.core.location.LocationRequestCompat.Quality

class Item(val title:String,val price : String,val description: String,val imageRestID: Int,var quantity: Int = 1) {
}