package com.example.coincapapp.feature.exchangeList.di

import com.example.coincapapp.feature.exchangeList.data.ExchangeRepository
import com.example.coincapapp.feature.exchangeList.data.ExchangeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface ExchangeRepositoryModule {

    @Binds
    fun bindExchangeRepository(impl: ExchangeRepositoryImpl): ExchangeRepository
}
