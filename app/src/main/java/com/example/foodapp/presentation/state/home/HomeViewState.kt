package com.example.foodapp.presentation.state.home

import com.example.foodapp.presentation.model.SearchedRecipesInfo

data class HomeViewState(
    val recipesList: List<SearchedRecipesInfo.SearchedRecipe>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)