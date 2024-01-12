package com.example.foodapp.domain.use_case.user

import com.example.foodapp.data.common.Resource
import com.example.foodapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return repository.register(email = email, password = password)
    }
}