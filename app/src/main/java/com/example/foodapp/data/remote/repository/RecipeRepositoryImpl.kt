package com.example.foodapp.data.remote.repository

import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.data.common.ResponseHandler
import com.example.foodapp.data.remote.RecipeService
import com.example.foodapp.data.remote.network.mapper.base.asResource
import com.example.foodapp.data.remote.network.mapper.toDomain
import com.example.foodapp.domain.model.GetDetailedRecipeInfo
import com.example.foodapp.domain.model.GetSearchedRecipesInfo
import com.example.foodapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService,
    private val responseHandler: ResponseHandler,
) : RecipeRepository {
    override suspend fun getRecipes(): Flow<ResourceApi<GetSearchedRecipesInfo>> {
        return responseHandler.handleApiCall {
            service.getRecipes()
        }.asResource { it.toDomain() }
    }

    override suspend fun getDetailsRecipe(id: Int): Flow<ResourceApi<GetDetailedRecipeInfo>> {
        return responseHandler.handleApiCall {
            service.getDetailsRecipe(id)
        }.asResource {
            it.toDomain()
        }
    }
}