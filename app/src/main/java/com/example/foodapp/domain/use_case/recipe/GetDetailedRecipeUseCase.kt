package com.example.foodapp.domain.use_case.recipe

import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetDetailedRecipeUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(id: Int) {
        recipeRepository.getDetailsRecipe(id = id)
    }
}