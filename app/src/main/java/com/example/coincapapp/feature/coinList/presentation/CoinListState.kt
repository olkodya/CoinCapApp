package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.runtime.Immutable

@Immutable
sealed class CoinListState {

    @Immutable
    data class Content(
        val coins: List<CoinState>,
    ) : CoinListState()

    data object Loading : CoinListState()
    @Immutable
    data class Error(
        val message: String,
        val onRefresh: () -> Unit,
     ) : CoinListState()
}

@Immutable
data class CoinState(
    val id: String,
    val rank: Long,
    val symbol: String,
    val name: String,
    val supply: Double,
    val maxSupply: Double,
    val marketCapUsd: Double,
    val volumeUsd24Hr: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
    val vwap24Hr: Double,
)
