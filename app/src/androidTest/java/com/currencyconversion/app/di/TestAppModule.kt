package com.currencyconversion.app.di

import androidx.test.core.app.ApplicationProvider
import com.currencyconversion.app.repositories.FakeConversionRepository
import com.currencyconversion.app.ui.viewModels.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    fun provideRepository() = MainViewModel(FakeConversionRepository(), mApplication = ApplicationProvider.getApplicationContext())
}