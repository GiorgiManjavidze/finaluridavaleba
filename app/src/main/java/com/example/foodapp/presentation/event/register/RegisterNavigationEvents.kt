package com.example.foodapp.presentation.event.register

sealed class RegisterNavigationEvents {
    data object NavigateToLogin : RegisterNavigationEvents()
}