package com.example.coincapapp.feature.coinDetail.domain

import com.example.coincapapp.feature.coinDetail.data.model.CoinDetailListResponse

interface GetCoinPriceHistoryUseCase {
    suspend operator fun invoke(coinId: String): CoinDetailListResponse

}
