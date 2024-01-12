package com.example.foodapp.data.remote

import com.example.foodapp.data.common.Constants
import com.example.foodapp.data.remote.network.model.DetailedRecipeInfoDto
import com.example.foodapp.data.remote.network.model.SearchedRecipesInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("complexSearch")
    @Headers("X-Api-Key: ${Constants.KEY}")
    suspend fun getRecipes(@Query("titleMatch") titleMatch: String? = null): Response<SearchedRecipesInfoDto>

    @GET("{id}/information")
    @Headers("X-Api-Key: ${Constants.KEY}")
    suspend fun getDetailsRecipe(@Path("id") id: Int): Response<DetailedRecipeInfoDto>

}