package com.example.foodapp.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.common.Resource
import com.example.foodapp.domain.remote.model.UserInfo
import com.example.foodapp.domain.remote.use_case.user.UserUseCase
import com.example.foodapp.domain.remote.use_case.validator.ValidatorUseCase
import com.example.foodapp.presentation.event.register.RegisterFragmentEvents
import com.example.foodapp.presentation.event.register.RegisterNavigationEvents
import com.example.foodapp.presentation.state.register.RegisterViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val validator: ValidatorUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterViewState(null))
    val registerState: StateFlow<RegisterViewState?> = _registerState

    private val _uiEvent = MutableSharedFlow<RegisterNavigationEvents>()
    val uiEvent: SharedFlow<RegisterNavigationEvents> get() = _uiEvent


    fun onEvent(event: RegisterFragmentEvents) {
        when (event) {
            is RegisterFragmentEvents.Register -> validateForm(event.email, event.password)
            is RegisterFragmentEvents.ResetErrorMessage -> updateErrorMessage(null)
        }
    }

    private fun register(email: String, password: String) = viewModelScope.launch {
        _registerState.update { it.copy(isLoading = true) }
        userUseCase.register(email, password).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _registerState.update {
                        it.copy(
                            resource = resource,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    saveUserInfoToFirebase(email, password)
                    _uiEvent.emit(RegisterNavigationEvents.NavigateToLogin)
                }

                is Resource.Failure -> {
                    _registerState.update {
                        it.copy(
                            resource = null,
                            isLoading = false,
                            errorMessage = resource.errorMessage
                        )
                    }
                }
            }
        }
    }

    private fun saveUserInfoToFirebase(email: String, password: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userInfo = UserInfo(email, password)
            val databaseReference = FirebaseDatabase.getInstance().getReference("users/${user.uid}")
            databaseReference.setValue(userInfo)
                .addOnFailureListener { e ->
                    _registerState.update {
                        it.copy(
                            errorMessage = e.message
                        )
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String) {
        val isEmailValid = validator.emailValidator(email)
        val isPasswordValid = validator.passwordValidator(password)

        val areFieldsValid = listOf(isEmailValid, isPasswordValid).all { it }

        if (!areFieldsValid) {
            updateErrorMessage(message = "Fields are not valid!")
            return
        }
        register(email, password)
    }

    private fun updateErrorMessage(message: String?) {
        _registerState.update { currentState -> currentState.copy(errorMessage = message) }
    }

}