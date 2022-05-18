package com.currencyconversion.app.data.repositories

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.service.network.NetworkResult
import dagger.Provides
import kotlinx.coroutines.flow.Flow

interface IConversionRepository {
    suspend fun responseExchangeRate(sourceCurrency: String): Flow<NetworkResult<ResponseExRate>>

    suspend fun responseAllCurrencies(): Flow<NetworkResult<ResponseCurrencies>>
}