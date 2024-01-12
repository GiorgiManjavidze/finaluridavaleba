package com.example.foodapp.di

import com.example.foodapp.data.remote.repository.RecipeRepositoryImpl
import com.example.foodapp.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RecipeRepositoryModule {
    @Binds
    @Singleton
    fun bindRecipeRepository(repositoryImpl: RecipeRepositoryImpl): RecipeRepository
}