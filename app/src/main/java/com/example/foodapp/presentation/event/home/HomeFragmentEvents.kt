package com.example.foodapp.presentation.event.home

sealed class HomeFragmentEvents {
    data object FetchRecipes : HomeFragmentEvents()
    data class FetchRecipesByTitle(val title: String) : HomeFragmentEvents()
    data object EditTextClick : HomeFragmentEvents()
    data object ResetErrorMessage : HomeFragmentEvents()
    data class ItemClick(val id: Int) : HomeFragmentEvents()
}