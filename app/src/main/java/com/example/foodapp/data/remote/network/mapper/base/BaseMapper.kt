package com.example.foodapp.data.remote.network.mapper.base


import com.example.foodapp.data.common.ResourceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun <Dto : Any, Domain : Any> Flow<ResourceApi<Dto>>.asResource(
    onSuccess: suspend (Dto) -> Domain,
): Flow<ResourceApi<Domain>> {
    return this.map {
        when (it) {
            is ResourceApi.Success -> ResourceApi.Success(data = onSuccess.invoke(it.data))
            is ResourceApi.Error -> ResourceApi.Error(errorMessage = it.errorMessage)
        }
    }
}