package com.example.foodapp.presentation.event.favourite

sealed class FavouriteFragmentEvents {
    data object GetRecipes : FavouriteFragmentEvents()
    data object ResetErrorMessage : FavouriteFragmentEvents()
    data class ItemClick(val id: Int) : FavouriteFragmentEvents()
    data class SlideToDelete(val title: String) : FavouriteFragmentEvents()
}