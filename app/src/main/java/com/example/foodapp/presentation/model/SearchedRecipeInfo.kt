package com.example.foodapp.presentation.model

data class SearchedRecipesInfo(
    val results: List<SearchedRecipe>?,
    val offset: Int?,
    val number: Int?,
    val totalResults: Int?,
) {
    data class SearchedRecipe(
        val id: Int,
        val title: String?,
        val image: String?,
        val imageType: String?,
        val isFavourite: Boolean
    )
}