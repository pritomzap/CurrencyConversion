package com.currencyconversion.app.repositories

import com.currencyconversion.app.ApiData
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.repositories.IConversionRepository
import com.currencyconversion.app.service.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class FakeConversionRepository :IConversionRepository {

    private var allCurrencies:Flow<NetworkResult<ResponseCurrencies>> = flowOf()

    override suspend fun responseExchangeRate(sourceCurrency: String): Flow<NetworkResult<ResponseExRate>> {
        return flowOf()
    }

    override suspend fun responseAllCurrencies(): Flow<NetworkResult<ResponseCurrencies>> {
        return allCurrencies
    }

    private fun currenciesSuccess(){
        allCurrencies = flowOf(NetworkResult.Success(ApiData.responseSuccessCurrencies))
    }
}