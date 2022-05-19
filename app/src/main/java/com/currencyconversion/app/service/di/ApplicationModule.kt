package com.deshi.personal.service.di

import android.content.Context
import com.currencyconversion.app.service.utils.ConnectionStateMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule{
    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext appContext: Context):Context = appContext

    @Singleton
    @Provides
    fun provideNetworkStateMonitor(@ApplicationContext appContext: Context) = ConnectionStateMonitor(appContext)
}