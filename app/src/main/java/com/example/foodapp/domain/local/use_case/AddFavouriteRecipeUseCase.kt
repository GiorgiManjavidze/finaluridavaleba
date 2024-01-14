package com.example.foodapp.domain.local.use_case

import com.example.foodapp.domain.local.model.FavouriteRecipeEntity
import com.example.foodapp.domain.local.repository.FavouriteRecipeRepository
import javax.inject.Inject

class AddFavouriteRecipeUseCase @Inject constructor(private val favouriteRecipeRepository: FavouriteRecipeRepository) {
    suspend operator fun invoke(recipe: FavouriteRecipeEntity) =
        favouriteRecipeRepository.addRecipe(recipe)
}