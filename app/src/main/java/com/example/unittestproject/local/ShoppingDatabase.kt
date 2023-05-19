package com.example.unittestproject.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDatabase :RoomDatabase(){

    abstract fun getDao():ShoppingDao
}