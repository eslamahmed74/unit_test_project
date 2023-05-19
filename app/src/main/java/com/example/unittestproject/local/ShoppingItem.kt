package com.example.unittestproject.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_table")
data class ShoppingItem(
    var name: String,
    var amount: Int,
    var imageUrl: String,
    var price: Float,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)