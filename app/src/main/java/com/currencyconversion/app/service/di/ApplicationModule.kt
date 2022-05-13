package com.deshi.personal.service.di

import android.content.Context
import com.currencyconversion.app.ui.customViews.CustomToast
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
    fun provideCustomToast(@ApplicationContext appContext: Context): CustomToast = CustomToast(appContext)

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext appContext: Context):Context = appContext

}