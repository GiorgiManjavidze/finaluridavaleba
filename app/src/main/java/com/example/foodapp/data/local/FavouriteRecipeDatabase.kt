package com.example.foodapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity

@Database(entities = [FavouriteRecipeEntity::class], version = 1)
abstract class FavouriteRecipeDatabase : RoomDatabase() {
    abstract fun favouriteRecipeDao(): FavouriteRecipeDao
}