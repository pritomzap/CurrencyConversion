package com.currencyconversion.app.service.network

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("currency_data/live")
    suspend fun getExchangeRate(@Query("source") source:String): Response<ResponseExRate>

    @GET("currency_data/list")
    suspend fun getAllCurrencies(): Response<ResponseCurrencies>

}