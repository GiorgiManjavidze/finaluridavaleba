package com.example.foodapp.data.common

sealed class ResourceApi<out D : Any> {
    data class Success<out D : Any>(val data: D) : ResourceApi<D>()
    data class Error<out D : Any>(val errorMessage: String) : ResourceApi<D>()
}