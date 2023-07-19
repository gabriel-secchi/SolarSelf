package com.gma.data_remote.network

interface NetworkEngine {
    enum class Method {
        POST
    }

    suspend fun <T: Any> postRequest(path: String, body: Any, responseClass: Class<T>): NetworkResult<T>
}