package com.currencyconversion.app.service.network

import com.currencyconversion.app.BuildConfig
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("api/latest.json")
    suspend fun getExchangeRate(@Query("app_id") appId:String = BuildConfig.API_KEY): Response<ResponseExRate>
}