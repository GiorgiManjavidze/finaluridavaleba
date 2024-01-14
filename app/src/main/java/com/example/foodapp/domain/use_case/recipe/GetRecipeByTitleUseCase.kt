package com.example.foodapp.domain.use_case.recipe

import com.example.foodapp.data.common.ResourceApi
import com.example.foodapp.domain.model.GetSearchedRecipesInfo
import com.example.foodapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeByTitleUseCase @Inject constructor(private val recipeRepository: RecipeRepository){
    suspend operator fun invoke(title: String): Flow<ResourceApi<GetSearchedRecipesInfo>> {
        return recipeRepository.getRecipeByTitle(title = title)
    }
}