package com.example.foodapp.presentation.state.search

import com.example.foodapp.presentation.model.SearchedRecipesInfo

data class SearchViewState(
    val recipesList: List<SearchedRecipesInfo.SearchedRecipe>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
