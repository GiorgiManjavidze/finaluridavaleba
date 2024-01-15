package com.example.foodapp.data.local.repository

import com.example.foodapp.data.local.FavouriteRecipeDao
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity
import com.example.foodapp.domain.local.repository.FavouriteRecipeRepository
import javax.inject.Inject

class FavouriteRecipeRepositoryImpl @Inject constructor(private val favouriteRecipeDao: FavouriteRecipeDao) :
    FavouriteRecipeRepository {
    override suspend fun getAllFavouriteRecipes(): List<FavouriteRecipeEntity> =
        favouriteRecipeDao.getAllRecipes()

    override suspend fun addRecipe(favouriteRecipeEntity: FavouriteRecipeEntity) =
        favouriteRecipeDao.addRecipe(favouriteRecipeEntity)

    override suspend fun removeRecipe(favouriteRecipeEntity: FavouriteRecipeEntity) =
        favouriteRecipeDao.removeRecipe(favouriteRecipeEntity)

    override suspend fun getRecipe(title: String): FavouriteRecipeEntity =
        favouriteRecipeDao.getRecipe(title)
}