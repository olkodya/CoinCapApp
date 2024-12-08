package com.example.coincapapp.feature.coinDetail.presentation

import java.math.BigDecimal

data class CoinDetailScreenState(
    val coinId: String,
    val coinName: String,
    val currentPrice: BigDecimal,
    val coinPriceHistory: List<CoinDetailState> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null
) {
    val isLoading: Boolean
        get() = loading
    val hasError: String?
        get() = errorMessage
}

data class CoinDetailState(
    val priceUsd: Float,
    val xValue: Float,
    val time: String,
)

