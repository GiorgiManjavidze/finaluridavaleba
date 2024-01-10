package com.example.foodapp.domain.use_case.validator

import javax.inject.Inject

class EmailValidatorUseCase @Inject constructor() {
    private val emailRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    operator fun invoke(email: String): Boolean {
        return email.isNotBlank() && email.matches(emailRegex)
    }
}