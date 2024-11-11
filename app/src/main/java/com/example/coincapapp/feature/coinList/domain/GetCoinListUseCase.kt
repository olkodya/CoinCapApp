package com.example.coincapapp.feature.coinList.domain

import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity

interface GetCoinListUseCase {
    suspend operator fun invoke(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<CoinEntity>
}