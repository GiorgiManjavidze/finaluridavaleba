package com.example.foodapp.domain.use_case.user

import com.example.foodapp.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() {
        repository.logout()
    }
}