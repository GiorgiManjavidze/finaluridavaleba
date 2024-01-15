package com.example.foodapp.presentation.event.search

sealed class SearchFragmentEvents {
    data class ItemClick(val id: Int) : SearchFragmentEvents()
    data class FetchRecipesByTitle(val title: String) : SearchFragmentEvents()
    data object ResetErrorMessage : SearchFragmentEvents()
}