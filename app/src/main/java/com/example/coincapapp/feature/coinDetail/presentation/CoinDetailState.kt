package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.runtime.Immutable
import java.math.BigDecimal

@Immutable
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
