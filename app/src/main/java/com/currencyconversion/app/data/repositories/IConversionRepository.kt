package com.currencyconversion.app.data.repositories

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.service.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface IConversionRepository {
    suspend fun responseExchangeRate(): Flow<NetworkResult<ResponseExRate>>
}