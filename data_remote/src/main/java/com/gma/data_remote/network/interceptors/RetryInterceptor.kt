package com.gma.data_remote.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

internal class RetryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)

        var tryCount = 0
        while (!response.isSuccessful && tryCount < MAX_RETRIES) {
            tryCount++

            val backoff = (Math.pow(2.0, tryCount.toDouble()) * 1000 ).toLong()
            Thread.sleep(backoff)
            request = request.newBuilder().build()
            response = chain.proceed(request)
        }

        return response
    }

    private companion object {
        const val MAX_RETRIES = 5
    }
}