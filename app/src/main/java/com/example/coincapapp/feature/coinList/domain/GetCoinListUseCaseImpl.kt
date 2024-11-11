package com.example.coincapapp.feature.coinList.domain

import com.example.coincapapp.feature.coinList.data.CoinRepository
import com.example.coincapapp.feature.coinList.data.model.toEntity
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import javax.inject.Inject

class GetCoinListUseCaseImpl @Inject constructor(
    private val repository: CoinRepository,
) : GetCoinListUseCase {
    override suspend fun invoke(searchQuery: String, limit: Int, offset: Int): List<CoinEntity> =
        repository.loadPagingCoins(searchQuery, offset, limit)
            .data.map { it.toEntity() }
}