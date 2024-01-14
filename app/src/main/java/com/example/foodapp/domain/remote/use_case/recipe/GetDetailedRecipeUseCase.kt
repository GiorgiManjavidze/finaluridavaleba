package com.example.foodapp.domain.remote.use_case.recipe

import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.domain.remote.model.GetDetailedRecipeInfo
import com.example.foodapp.domain.remote.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailedRecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(itemId: Int): Flow<ResourceApi<GetDetailedRecipeInfo>> {
        return recipeRepository.getDetailsRecipe(itemId = itemId)
    }
}