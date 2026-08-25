package com.bochen.loginsight.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface LogApi {

    @GET("logs")
    suspend fun getLogs(): Response<List<LogDto>>
}