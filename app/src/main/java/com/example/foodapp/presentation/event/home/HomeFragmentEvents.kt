package com.example.foodapp.presentation.event.home

sealed class HomeFragmentEvents {
    data object FetchRecipes : HomeFragmentEvents()
    data object ResetErrorMessage : HomeFragmentEvents()
}