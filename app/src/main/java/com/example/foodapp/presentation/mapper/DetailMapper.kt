package com.example.foodapp.presentation.mapper

import com.example.foodapp.domain.model.GetDetailedRecipeInfo
import com.example.foodapp.presentation.model.DetailedRecipeInfo

fun GetDetailedRecipeInfo.GetExtendedIngredient.toPresentation(): DetailedRecipeInfo.ExtendedIngredient {
    return DetailedRecipeInfo.ExtendedIngredient(
        aisle = aisle,
        amount = amount,
        consistency = consistency,
        id = id,
        image = image,
        measures = getMeasures.toPresentation(),
        meta = meta,
        name = name,
        nameClean = nameClean,
        original = original,
        originalName = originalName,
        unit = unit
    )
}

fun GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.toPresentation(): DetailedRecipeInfo.ExtendedIngredient.Measures {
    return DetailedRecipeInfo.ExtendedIngredient.Measures(
        metric = getMetric.toPresentation(),
        us = getUs.toPresentation()
    )
}

fun GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.GetMetric.toPresentation(): DetailedRecipeInfo.ExtendedIngredient.Measures.Metric {
    return DetailedRecipeInfo.ExtendedIngredient.Measures.Metric(
        amount = amount,
        unitLong = unitLong,
        unitShort = unitShort
    )
}

fun GetDetailedRecipeInfo.GetExtendedIngredient.GetMeasures.GetUs.toPresentation(): DetailedRecipeInfo.ExtendedIngredient.Measures.Us {
    return DetailedRecipeInfo.ExtendedIngredient.Measures.Us(
        amount = amount,
        unitLong = unitLong,
        unitShort = unitShort
    )
}

fun GetDetailedRecipeInfo.GetWinePairing.toPresentation(): DetailedRecipeInfo.WinePairing {
    return DetailedRecipeInfo.WinePairing(
        pairedWines = pairedWines,
        pairingText = pairingText,
        productMatches = productMatches
    )
}

fun GetDetailedRecipeInfo.toPresentation(): DetailedRecipeInfo {
    return DetailedRecipeInfo(
        aggregateLikes = aggregateLikes,
        analyzedInstructions = analyzedInstructions,
        cheap = cheap,
        cookingMinutes = cookingMinutes,
        creditsText = creditsText,
        cuisines = cuisines,
        dairyFree = dairyFree,
        diets = diets,
        dishTypes = dishTypes,
        extendedIngredients = getExtendedIngredients.map { it.toPresentation() },
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
        winePairing = getWinePairing.toPresentation()
    )
}
