package com.example.foodapp.presentation.state.log_in

import com.example.foodapp.data.common.Resource
import com.google.firebase.auth.FirebaseUser

data class LoginViewState(
    val resource: Resource<FirebaseUser>?,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
