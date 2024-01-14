package com.example.foodapp.presentation.state.detail

import com.example.foodapp.presentation.model.DetailedRecipeInfo

data class DetailViewState (
    val recipe: DetailedRecipeInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)