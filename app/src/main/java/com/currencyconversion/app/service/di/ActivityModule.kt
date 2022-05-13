package com.deshi.personal.service.di

import com.currencyconversion.app.BuildConfig
import com.currencyconversion.app.service.network.ApiService
import com.deshi.personal.service.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {
    @Provides
    fun provideRetrofit(): Retrofit = ApiClient().getRetrofit(BuildConfig.BASE_URL)!!

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}