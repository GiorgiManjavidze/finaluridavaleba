package com.example.foodapp.domain.repository

import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.domain.model.GetDetailedRecipeInfo
import com.example.foodapp.domain.model.GetSearchedRecipesInfo
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(): Flow<ResourceApi<GetSearchedRecipesInfo>>
    suspend fun getDetailsRecipe(id: Int): Flow<ResourceApi<GetDetailedRecipeInfo>>
}