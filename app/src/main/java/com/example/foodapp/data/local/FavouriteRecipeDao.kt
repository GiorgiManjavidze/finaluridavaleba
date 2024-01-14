package com.example.foodapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRecipeDao {

    @Query("SELECT * FROM favourite_recipes ORDER BY id ASC")
    suspend fun getAllRecipes(): List<FavouriteRecipeEntity>

    @Query("SELECT * FROM favourite_recipes WHERE title = :title")
    suspend fun getRecipe(title: String): FavouriteRecipeEntity

    @Delete
    suspend fun removeRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)
}