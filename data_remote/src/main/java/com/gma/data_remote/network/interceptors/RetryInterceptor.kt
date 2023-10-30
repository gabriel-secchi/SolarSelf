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

            Thread.sleep(TIME_SLEEP_RETRIE)
            request = request.newBuilder().build()
            response = chain.proceed(request)
        }

        return response
    }

    private companion object {
        const val TIME_SLEEP_RETRIE = 1000L
        const val MAX_RETRIES = 5
    }
}