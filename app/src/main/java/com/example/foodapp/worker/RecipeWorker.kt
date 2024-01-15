package com.example.foodapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.foodapp.data.remote.RecipeService
import javax.inject.Inject

class RecipeWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    @Inject
    lateinit var recipeService: RecipeService

    override suspend fun doWork(): Result {
        return try {
            val titleMatch = inputData.getString("titleMatch")
            val recipeId = inputData.getInt("recipeId", -1)

            val recipeResponse = if (titleMatch != null) {
                recipeService.getRecipes(titleMatch)
            } else if (recipeId != -1) {
                recipeService.getDetailsRecipe(recipeId)
            } else {
                throw IllegalArgumentException("Invalid input for RecipeWorker")
            }

            if (recipeResponse.isSuccessful) {
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}