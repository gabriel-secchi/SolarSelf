package com.gma.data_remote.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST
    suspend fun postRequest(
        @Url url: String,
        @Body body: RequestBody,
        //@HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>

}