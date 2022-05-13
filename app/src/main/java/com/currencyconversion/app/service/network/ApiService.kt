package com.currencyconversion.app.service.network

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("currency_data/live")
    suspend fun getExchangeRate(): Response<ResponseExRate>
}