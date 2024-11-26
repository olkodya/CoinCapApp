package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class CoinDetailState(
    val coinId: String,
    val coinName: String,
    val currentPrice: String,
    val coinPriceHistory: List<String>
)

