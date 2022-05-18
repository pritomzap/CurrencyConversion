package com.deshi.personal.service.di

import android.content.Context
import com.currencyconversion.app.BuildConfig
import com.currencyconversion.app.data.remoteDataSource.RemoteDataSource
import com.currencyconversion.app.data.repositories.ConversionRepository
import com.currencyconversion.app.data.repositories.IConversionRepository
import com.currencyconversion.app.service.network.ApiService
import com.deshi.personal.service.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {
    @Provides
    fun provideRetrofit(): Retrofit = ApiClient().getRetrofit(BuildConfig.BASE_URL)!!

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideConversionRepository(remoteDataSource: RemoteDataSource, @ApplicationContext appContext: Context) = ConversionRepository(remoteDataSource,appContext) as IConversionRepository

    @Provides
    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)
}