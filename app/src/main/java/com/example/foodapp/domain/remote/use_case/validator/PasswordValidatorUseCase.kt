package com.example.foodapp.domain.remote.use_case.validator

import javax.inject.Inject

class PasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean = password.isNotBlank()
}