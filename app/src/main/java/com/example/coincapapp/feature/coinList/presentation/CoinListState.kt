package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.runtime.Immutable
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity


@Immutable
sealed class CoinListState {

    @Immutable
    data class Content(
        val coins: List<CoinState>,
    ) : CoinListState()

    data object InitialLoading : CoinListState()

    data object NextPageLoading : CoinListState()


    @Immutable
    data class Error(
        val message: String,
        val onRefresh: () -> Unit,
    ) : CoinListState()

    data class NextPageError(
        val message: String,
        val onRefresh: () -> Unit,
    ) : CoinListState()
}

@Immutable
data class CoinState(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String,
    val marketCapUsd: String,
    val volumeUsd24Hr: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val vwap24Hr: String,
    val explorer: String
)

fun CoinEntity.toState() = CoinState(
    id = id ?: "",
    rank = rank ?: "",
    symbol = symbol ?: "",
    name = name ?: "",
    supply = supply ?: "",
    maxSupply = maxSupply ?: "",
    marketCapUsd = marketCapUsd ?: "",
    volumeUsd24Hr = volumeUsd24Hr ?: "",
    priceUsd = priceUsd ?: "",
    changePercent24Hr = changePercent24Hr ?: "",
    vwap24Hr = vwap24Hr ?: "",
    explorer = explorer ?: "",
)

fun CoinState.toEntity() = CoinEntity(
    id = id,
    rank = rank,
    symbol = symbol,
    name = name,
    supply = supply,
    maxSupply = maxSupply,
    marketCapUsd = marketCapUsd,
    volumeUsd24Hr = volumeUsd24Hr,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
    vwap24Hr = vwap24Hr,
    explorer = explorer
)
