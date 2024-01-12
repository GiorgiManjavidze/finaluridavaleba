package com.example.foodapp.presentation.event.log_in

sealed class LoginFragmentEvents {
    data class Login(val email: String, val password: String) : LoginFragmentEvents()
    data object ResetErrorMessage : LoginFragmentEvents()
}