package com.example.coincapapp.feature.coinList.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponse(
    val data: List<CoinResponse>
)

@Serializable
data class CoinResponse(
    val id: String?,
    val rank: String?,
    val symbol: String?,
    val name: String?,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val volumeUsd24Hr: String?,
    val priceUsd: String?,
    val changePercent24Hr: String?,
    val vwap24Hr: String?,
)
