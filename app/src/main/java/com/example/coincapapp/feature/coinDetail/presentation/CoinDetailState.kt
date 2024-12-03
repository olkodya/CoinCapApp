package com.example.coincapapp.feature.coinDetail.presentation

import java.math.BigDecimal

data class CoinDetailState(
    val coinId: String,
    val coinName: String,
    val currentPrice: BigDecimal,
    val coinPriceHistory: List<BigDecimal> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null
) {
    val isLoading: Boolean
        get() = loading
    val hasError: String?
        get() = errorMessage
}
