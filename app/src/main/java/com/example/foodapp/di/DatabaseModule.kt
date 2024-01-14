package com.example.foodapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodapp.data.local.FavouriteRecipeDao
import com.example.foodapp.data.local.FavouriteRecipeDatabase
import com.example.foodapp.data.local.repository.FavouriteRecipeRepositoryImpl
import com.example.foodapp.domain.local.repository.FavouriteRecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): FavouriteRecipeDatabase =
        Room.databaseBuilder(
            context, FavouriteRecipeDatabase::class.java, "RECIPE_DATABASE"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: FavouriteRecipeDatabase) = db.favouriteRecipeDao()

    @Provides
    fun provideFavouriteRecipeRepository(favouriteRecipeDao: FavouriteRecipeDao): FavouriteRecipeRepository {
        return FavouriteRecipeRepositoryImpl(favouriteRecipeDao)
    }
}