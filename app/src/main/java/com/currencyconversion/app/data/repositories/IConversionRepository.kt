package com.currencyconversion.app.data.repositories

import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.service.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface IConversionRepository {
    suspend fun responseExchangeRate(sourceCurrency: String): Flow<NetworkResult<ResponseExRate>>

    suspend fun responseAllCurrencies(): Flow<NetworkResult<ResponseCurrencies>>

    fun setAllCurrenciesData(data:NetworkResult<ResponseCurrencies>)

    fun setExchangeRate(data:NetworkResult<ResponseExRate>)
}