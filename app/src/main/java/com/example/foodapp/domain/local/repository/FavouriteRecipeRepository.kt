package com.example.foodapp.domain.local.repository

import com.example.foodapp.domain.local.model.FavouriteRecipeEntity

interface FavouriteRecipeRepository {
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity>
    suspend fun addRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)
    suspend fun removeRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)
    suspend fun getRecipe(title: String): FavouriteRecipeEntity
}