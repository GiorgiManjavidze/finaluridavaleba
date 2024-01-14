package com.example.foodapp.data.remote.network.model

import com.squareup.moshi.Json

data class SearchedRecipesInfoDto(
    @Json(name = "results")
    val results: List<SearchedRecipeDto>,
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "number")
    val number: Int?,
    @Json(name = "totalResults")
    val totalResults: Int?,
) {
    data class SearchedRecipeDto(
        @Json(name = "id")
        val id: Int,
        @Json(name = "title")
        val title: String?,
        @Json(name = "image")
        val image: String?,
        @Json(name = "imageType")
        val imageType: String?,
    )
}