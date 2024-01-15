package com.example.foodapp.data.remote.repository

import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.data.common.ResponseHandler
import com.example.foodapp.data.local.FavouriteRecipeDao
import com.example.foodapp.data.remote.RecipeService
import com.example.foodapp.data.remote.network.mapper.base.asResource
import com.example.foodapp.data.remote.network.mapper.toDomain
import com.example.foodapp.domain.local.model.FavouriteRecipeEntity
import com.example.foodapp.domain.remote.model.GetDetailedRecipeInfo
import com.example.foodapp.domain.remote.model.GetSearchedRecipesInfo
import com.example.foodapp.domain.remote.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService,
    private val responseHandler: ResponseHandler,
    private val favouriteRecipeDao: FavouriteRecipeDao
) : RecipeRepository {
    override suspend fun getRecipes(): Flow<ResourceApi<GetSearchedRecipesInfo>> {
        return responseHandler.handleApiCall {
            service.getRecipes()
        }.asResource { it.toDomain() }
    }

    override suspend fun getDetailsRecipe(itemId: Int): Flow<ResourceApi<GetDetailedRecipeInfo>> {
        return responseHandler.handleApiCall {
            service.getDetailsRecipe(itemId)
        }.asResource {
            it.toDomain()
        }
    }

    override suspend fun getRecipeByTitle(title: String): Flow<ResourceApi<GetSearchedRecipesInfo>> {
        return responseHandler.handleApiCall {
            service.getRecipes(titleMatch = title)
        }.asResource { it.toDomain() }
    }

    override suspend fun getFavourites(): List<FavouriteRecipeEntity> =
        favouriteRecipeDao.getAllRecipes()

    override suspend fun addRecipe(recipe: FavouriteRecipeEntity) =
        favouriteRecipeDao.addRecipe(recipe)

    override suspend fun removeRecipe(recipe: FavouriteRecipeEntity) =
        favouriteRecipeDao.removeRecipe(recipe)
}