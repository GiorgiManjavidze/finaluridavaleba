package com.example.foodapp.di

import com.example.foodapp.data.repository.AuthRepositoryImpl
import com.example.foodapp.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindUserRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
}