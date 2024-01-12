package com.example.foodapp.domain.use_case.recipe

import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke() = recipeRepository.getRecipes()
}