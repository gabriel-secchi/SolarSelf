package com.gma.data_remote.network

sealed class NetworkResult<out T: Any> {
    data class Success<out T: Any>(
        val data: T,
        val headers: Map<String, String>? = null
    ) : NetworkResult<T>()

    data class Error<out T: Any>(
        val exception: Throwable,
        val response: T?
    ) : NetworkResult<T>()
}
