package com.example.foodapp.presentation.event.detail

sealed class DetailFragmentEvent {
    data class FetchRecipe(val itemId: Int) : DetailFragmentEvent()
    data object ResetErrorMessage : DetailFragmentEvent()
}