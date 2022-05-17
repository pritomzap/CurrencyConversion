package com.currencyconversion.app.data.remoteDataSource

import com.currencyconversion.app.service.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiServices: ApiService) {

    suspend fun getExchangeRate(sourceCurrency:String) = apiServices.getExchangeRate(source = sourceCurrency)

    suspend fun getCurrencies() = apiServices.getAllCurrencies()
}