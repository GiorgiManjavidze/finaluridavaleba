package com.example.foodapp.data.remote.network.model

import com.squareup.moshi.Json

data class DetailedRecipeInfoDto(
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int?,
    @Json(name = "analyzedInstructions")
    val analyzedInstructions: List<Any?>?,
    @Json(name = "cheap")
    val cheap: Boolean?,
    @Json(name = "cookingMinutes")
    val cookingMinutes: Int?,
    @Json(name = "creditsText")
    val creditsText: String?,
    @Json(name = "cuisines")
    val cuisines: List<Any?>?,
    @Json(name = "dairyFree")
    val dairyFree: Boolean?,
    @Json(name = "diets")
    val diets: List<Any?>?,
    @Json(name = "dishTypes")
    val dishTypes: List<String?>?,
    @Json(name = "extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient?>?,
    @Json(name = "gaps")
    val gaps: String?,
    @Json(name = "glutenFree")
    val glutenFree: Boolean?,
    @Json(name = "healthScore")
    val healthScore: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "imageType")
    val imageType: String?,
    @Json(name = "instructions")
    val instructions: String?,
    @Json(name = "license")
    val license: String?,
    @Json(name = "lowFodmap")
    val lowFodmap: Boolean?,
    @Json(name = "occasions")
    val occasions: List<Any?>?,
    @Json(name = "originalId")
    val originalId: Any?,
    @Json(name = "preparationMinutes")
    val preparationMinutes: Int?,
    @Json(name = "pricePerServing")
    val pricePerServing: Double?,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int?,
    @Json(name = "servings")
    val servings: Int?,
    @Json(name = "sourceName")
    val sourceName: String?,
    @Json(name = "sourceUrl")
    val sourceUrl: String?,
    @Json(name = "spoonacularScore")
    val spoonacularScore: Double?,
    @Json(name = "spoonacularSourceUrl")
    val spoonacularSourceUrl: String?,
    @Json(name = "summary")
    val summary: String?,
    @Json(name = "sustainable")
    val sustainable: Boolean?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "vegan")
    val vegan: Boolean?,
    @Json(name = "vegetarian")
    val vegetarian: Boolean?,
    @Json(name = "veryHealthy")
    val veryHealthy: Boolean?,
    @Json(name = "veryPopular")
    val veryPopular: Boolean?,
    @Json(name = "weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?,
    @Json(name = "winePairing")
    val winePairing: WinePairing?
) {
    data class ExtendedIngredient(
        @Json(name = "aisle")
        val aisle: String?,
        @Json(name = "amount")
        val amount: Double?,
        @Json(name = "consistency")
        val consistency: String?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "image")
        val image: String?,
        @Json(name = "measures")
        val measures: Measures?,
        @Json(name = "meta")
        val meta: List<String?>?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "nameClean")
        val nameClean: String?,
        @Json(name = "original")
        val original: String?,
        @Json(name = "originalName")
        val originalName: String?,
        @Json(name = "unit")
        val unit: String?
    ) {
        data class Measures(
            @Json(name = "metric")
            val metric: Metric?,
            @Json(name = "us")
            val us: Us?
        ) {
            data class Metric(
                @Json(name = "amount")
                val amount: Double?,
                @Json(name = "unitLong")
                val unitLong: String?,
                @Json(name = "unitShort")
                val unitShort: String?
            )

            data class Us(
                @Json(name = "amount")
                val amount: Double?,
                @Json(name = "unitLong")
                val unitLong: String?,
                @Json(name = "unitShort")
                val unitShort: String?
            )
        }
    }

    data class WinePairing(
        @Json(name = "pairedWines")
        val pairedWines: List<Any?>?,
        @Json(name = "pairingText")
        val pairingText: String?,
        @Json(name = "productMatches")
        val productMatches: List<Any?>?
    )
}
