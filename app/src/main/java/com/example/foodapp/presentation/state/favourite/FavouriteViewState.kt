package com.example.foodapp.presentation.state.favourite

import com.example.foodapp.domain.local.model.FavouriteRecipeEntity

data class FavouriteViewState(
    val favouriteRecipes: List<FavouriteRecipeEntity>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)