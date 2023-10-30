package com.gma.data_remote.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val TIME_OUT: Long = 30

fun okHttpClientBuilder(
    interceptors: List<Interceptor>
): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)

    interceptors.forEach {
        clientBuilder.addInterceptor(it)
    }

    return clientBuilder.build()

}

fun buildApiService(
    client: OkHttpClient
): ApiService {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .baseUrl("https://none.na/")
        .build()
        .create(ApiService::class.java)
}
