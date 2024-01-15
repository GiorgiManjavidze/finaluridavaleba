package com.example.foodapp.presentation.event.search

sealed interface SearchNavigationEvents {
    data class NavigateToDetails(val id: Int) : SearchNavigationEvents
    data object NavigateToHome : SearchNavigationEvents
}