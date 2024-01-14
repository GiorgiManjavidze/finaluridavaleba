package com.example.foodapp.data.remote.network.mapper

import com.example.foodapp.data.remote.network.model.DetailedRecipeInfoDto
import com.example.foodapp.domain.remote.model.GetDetailedRecipeInfo


fun DetailedRecipeInfoDto.toDomain(): GetDetailedRecipeInfo {
    return GetDetailedRecipeInfo(
        aggregateLikes = aggregateLikes,
        analyzedInstructions = analyzedInstructions,
        cheap = cheap,
        cookingMinutes = cookingMinutes,
        creditsText = creditsText,
        cuisines = cuisines,
        dairyFree = dairyFree,
        diets = diets,
        dishTypes = dishTypes,
        getExtendedIngredients = extendedIngredients!!.map { it!!.toDomain() },
        gaps = gaps,
        glutenFree = glutenFree,
        healthScore = healthScore,
        id = id,
        image = image,
        imageType = imageType,
        instructions = instructions,
        license = license,
        lowFodmap = lowFodmap,
        occasions = occasions,
        originalId = originalId,
        preparationMinutes = preparationMinutes,
        pricePerServing = pricePerServing,
        readyInMinutes = readyInMinutes,
        servings = servings,
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        spoonacularScore = spoonacularScore,
        spoonacularSourceUrl = spoonacularSourceUrl,
        summary = summary,
        sustainable = sustainable,
        title = title,
        vegan = vegan,
        vegetarian = vegetarian,
        veryHealthy = veryHealthy,
        veryPopular = veryPopular,
        weightWatcherSmartPoints = weightWatcherSmartPoints,
        getWinePairing = winePairing!!.toDomain()
    )
}

fun DetailedRecipeInfoDto.ExtendedIngredient.toDomain(): GetDetailedRecipeInfo.GetExtendedIngredient {
    return GetDetailedRecipeInfo.GetExtendedIngredient(
        aisle = aisle,
        amount = amount,
        consistency = consistency,
        id = id,
        image = image,
        getMeasures = measures!!.toDomain(),
        meta = meta,
        name = name,
        nameClean = nameClean,
        original = original,
        originalName = originalName,
        unit = unit
    )
}

fun DetailedRecipeInfoDto.ExtendedIngredient.Measures.toDomain(): GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures {
    return GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures(
        getMetric = metric!!.toDomain(),
        getUs = us!!.toDomain()

    )
}

fun DetailedRecipeInfoDto.ExtendedIngredient.Measures.Metric.toDomain(): GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.GetMetric {
    return GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.GetMetric(
        amount = amount,
        unitLong = unitLong,
        unitShort = unitShort
    )
}

fun DetailedRecipeInfoDto.ExtendedIngredient.Measures.Us.toDomain(): GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.GetUs {
    return GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.GetUs(
        amount = amount,
        unitLong = unitLong,
        unitShort = unitShort
    )
}

fun DetailedRecipeInfoDto.WinePairing.toDomain(): GetDetailedRecipeInfo.GetWinePairing {
    return GetDetailedRecipeInfo.GetWinePairing(
        pairedWines = pairedWines,
        pairingText = pairingText,
        productMatches = productMatches
    )
}

