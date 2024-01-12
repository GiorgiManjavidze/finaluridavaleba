package com.example.foodapp.presentation.event.register

sealed class RegisterFragmentEvents {
    data class Register(val email: String, val password: String) : RegisterFragmentEvents()
    data object ResetErrorMessage : RegisterFragmentEvents()
}