package com.example.foodapp.presentation.screen.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.common.Resource
import com.example.foodapp.domain.remote.use_case.user.UserUseCase
import com.example.foodapp.domain.remote.use_case.validator.ValidatorUseCase
import com.example.foodapp.presentation.event.log_in.LoginFragmentEvents
import com.example.foodapp.presentation.event.log_in.LoginNavigationEvents
import com.example.foodapp.presentation.state.log_in.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val validator: ValidatorUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState(null))
    val loginState: StateFlow<LoginViewState?> = _viewState

    private val _uiEvent = MutableSharedFlow<LoginNavigationEvents>()
    val uiEvent: SharedFlow<LoginNavigationEvents> get() = _uiEvent

    fun onEvent(event: LoginFragmentEvents) {
        when (event) {
            is LoginFragmentEvents.Login -> validateForm(
                email = event.email,
                password = event.password
            )

            is LoginFragmentEvents.ResetErrorMessage -> updateErrorMessage(null)
        }
    }

    private fun login(email: String, password: String) = viewModelScope.launch {
        _viewState.update { it.copy(isLoading = true) }
        userUseCase.login(email, password).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _viewState.update {
                        it.copy(
                            resource = resource,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    _uiEvent.emit(LoginNavigationEvents.NavigateToHome)
                }

                is Resource.Failure -> {
                    _viewState.update {
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

    private fun validateForm(email: String, password: String) {
        val isEmailValid = validator.emailValidator(email)
        val isPasswordValid = validator.passwordValidator(password)

        val areFieldsValid = listOf(isEmailValid, isPasswordValid).all { it }

        if (!areFieldsValid) {
            updateErrorMessage(message = "Fields are not valid!")
            return
        }
        login(email, password)
    }

    private fun updateErrorMessage(message: String?) {
        _viewState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}