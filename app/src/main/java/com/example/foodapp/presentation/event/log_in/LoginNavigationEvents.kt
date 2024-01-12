package com.example.foodapp.presentation.event.log_in

sealed class LoginNavigationEvents {
    data object NavigateToHome : LoginNavigationEvents()
    data object NavigateToRegister : LoginNavigationEvents()
}