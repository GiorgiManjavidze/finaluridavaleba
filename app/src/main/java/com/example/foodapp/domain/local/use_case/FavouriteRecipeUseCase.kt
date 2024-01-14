package com.example.foodapp.domain.local.use_case

import javax.inject.Inject

data class FavouriteRecipeUseCase @Inject constructor(
    val addRecipe: AddFavouriteRecipeUseCase,
    val getRecipe: GetFavouriteRecipeUseCase,
    val getAllRecipes: GetAllFavouriteRecipeUseCase,
    val deleteRecipe: DeleteFavouriteRecipeUseCase
)
