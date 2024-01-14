package com.example.foodapp.domain.remote.use_case.validator

import javax.inject.Inject

data class ValidatorUseCase @Inject constructor(
    val emailValidator: EmailValidatorUseCase,
    val passwordValidator: PasswordValidatorUseCase
)
