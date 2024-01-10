package com.example.foodapp.data.utils

import com.example.foodapp.data.Resource
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HandleFirebaseResponse @Inject constructor() {
     suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Flow<Resource<T>> = flow {
        try {
            val response = apiCall()
            emit(Resource.Success(response))
        } catch (e: FirebaseAuthEmailException) {
            emit(Resource.Failure(e.message!!))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Resource.Failure(e.message!!))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Failure(e.message!!))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Failure(e.message!!))
        } catch (e: FirebaseAuthActionCodeException) {
            emit(Resource.Failure(e.message!!))
        }
    }
}