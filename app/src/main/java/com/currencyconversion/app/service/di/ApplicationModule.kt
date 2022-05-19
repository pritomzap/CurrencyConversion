package com.deshi.personal.service.di

import android.content.Context
import com.currencyconversion.app.data.remoteDataSource.RemoteDataSource
import com.currencyconversion.app.data.repositories.ConversionRepository
import com.currencyconversion.app.data.repositories.IConversionRepository
import com.currencyconversion.app.service.network.ApiService
import com.currencyconversion.app.service.utils.ConnectionStateMonitor
import com.currencyconversion.app.ui.customViews.CustomToast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.sql.Connection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule{

    @Singleton
    @Provides
    fun provideCustomToast(@ApplicationContext appContext: Context): CustomToast = CustomToast(appContext)

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext appContext: Context):Context = appContext

    @Singleton
    @Provides
    fun provideNetworkStateMonitor(@ApplicationContext appContext: Context) = ConnectionStateMonitor(appContext)
}