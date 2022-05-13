package com.currencyconversion.app.data.remoteDataSource

import com.currencyconversion.app.service.network.ApiService
import javax.inject.Inject

open class RemoteDataSource @Inject constructor(private val apiServices: ApiService) {
    suspend fun getExchangeRate() = apiServices.getExchangeRate()
}