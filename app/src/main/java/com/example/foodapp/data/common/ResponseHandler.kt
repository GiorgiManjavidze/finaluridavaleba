package com.example.foodapp.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ResponseHandler @Inject constructor() {
    fun <T : Any> handleApiCall(apiCall: suspend () -> Response<T>): Flow<ResourceApi<T>> = flow {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(ResourceApi.Success(response.body()!!))
            } else {
                emit(ResourceApi.Error("Error Code: ${response.code()}"))
            }
        } catch (e: IOException) {
            emit(ResourceApi.Error("Network error: ${e.message}"))
        } catch (e: HttpException) {
            emit(ResourceApi.Error("HTTP error: ${e.message}"))
        } catch (e: Exception) {
            emit(ResourceApi.Error("Unknown error: ${e.message}"))
        }
    }
}