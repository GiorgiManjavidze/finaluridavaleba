package com.example.foodapp.presentation.event.home

sealed interface HomeNavigationEvents {
    data object NavigateToDetails : HomeNavigationEvents
}