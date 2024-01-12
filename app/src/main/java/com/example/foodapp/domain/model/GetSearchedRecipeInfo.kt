package com.example.foodapp.domain.model

data class GetSearchedRecipesInfo(
    val results: List<GetSearchedRecipe>,
    val offset: Int?,
    val number: Int?,
    val totalResults: Int?,
) {
    data class GetSearchedRecipe(
        val id: Int?,
        val title: String?,
        val image: String?,
        val imageType: String?
    )
}