package com.gma.data_remote.network

import com.gma.data_remote.network.NetworkEngine.Method
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class NetworkEngineImpl(
    private val apiDataAccess: ApiDataEngine,
    private val apiService: ApiService,
    private val gson: Gson,
) : NetworkEngine {

    override suspend fun <T : Any> postRequest(
        path: String,
        body: Any,
        responseClass: Class<T>
    ): NetworkResult<T> {
        return makeRequest(Method.POST, path, body, responseClass)
    }

    private suspend fun <T : Any> makeRequest(
        method: Method,
        path: String,
        body: Any,
        responseClass: Class<T>
    ): NetworkResult<T> {
        return safeRequest {

            val response = when (method) {
                Method.POST -> apiService.postRequest(
                    url = getUriEndPoint(path),
                    body = body.toRequestBody()
                )
            }

            val responseBody = response.body()?.string() ?: ""
            val errorBody = response.errorBody()?.string() ?: ""
            if (response.isSuccessful && responseBody.isNotEmpty()) {

                NetworkResult.Success(
                    data = gson.fromJson(responseBody, responseClass),
                    headers = response.headers().toMap()
                )
            } else {
                NetworkResult.Error(
                    exception = Exception(errorBody),
                    null
                )
            }
        }
    }

    private suspend fun <T : Any> safeRequest(apiCall: suspend () -> NetworkResult<T>): NetworkResult<T> =
        withContext(IO) {
            try {
                checkNetworkConnection()
                apiCall()
            } catch (throwable: Throwable) {
                NetworkResult.Error(throwable, null)
            }
        }

    private fun getUriEndPoint(path: String): String {
        val url = apiDataAccess.getApiData().apiUrl.trim()
        if (url.endsWith("/"))
            return url.plus(path)

        return url.plus("/${path}")
    }

    private fun checkNetworkConnection() {
        //TODO:validation connetionor throw
    }

    private fun Any.toRequestBody(): RequestBody {
        return toJson().jsonToRequestBody()
    }

    private fun Any.toJson(): String {
        return gson.toJson(this)
    }

    private fun String.jsonToRequestBody(): RequestBody {
        return toRequestBody(APPLICATION_JSON_UTF8.toMediaType())
    }

    private companion object {
        const val APPLICATION_JSON_UTF8 = "application/json; charset=utf-8"
    }
}