package com.currencyconversion.app.data.repositories

import android.content.Context
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseCurrencies
import com.currencyconversion.app.data.models.responses.responseExRate.ResponseExRate
import com.currencyconversion.app.data.remoteDataSource.RemoteDataSource
import com.currencyconversion.app.service.network.NetworkResult
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ActivityRetainedScoped
class ConversionRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext context: Context
): BaseRepository(context),IConversionRepository {

    override suspend fun responseExchangeRate(sourceCurrency:String): Flow<NetworkResult<ResponseExRate>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getExchangeRate(sourceCurrency) })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun responseAllCurrencies(): Flow<NetworkResult<ResponseCurrencies>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getCurrencies() })
        }.flowOn(Dispatchers.IO)
    }
}