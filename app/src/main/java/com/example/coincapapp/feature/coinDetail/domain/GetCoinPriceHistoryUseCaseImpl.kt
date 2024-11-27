package com.example.coincapapp.feature.coinDetail.domain

import com.example.coincapapp.feature.coinDetail.data.CoinDetailRepository
import com.example.coincapapp.feature.coinDetail.data.model.CoinDetailListResponse
import javax.inject.Inject

class GetCoinPriceHistoryUseCaseImpl @Inject constructor(
    private val repository: CoinDetailRepository
) : GetCoinPriceHistoryUseCase {
    override suspend fun invoke(coinId: String): CoinDetailListResponse =
        repository.getCoinPricesHistory(coinId)
}