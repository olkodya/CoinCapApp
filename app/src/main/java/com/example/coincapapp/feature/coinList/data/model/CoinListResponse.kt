package com.example.coincapapp.feature.coinList.data.model

import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponse(
    val data: List<CoinResponse>
)

@Serializable
data class CoinResponse(
    @SerialName("id")
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
    val explorer: String?,
)

fun CoinResponse.toEntity() = CoinEntity(
    id = id,
    rank = rank,
    symbol = symbol,
    name = name,
    supply = supply,
    maxSupply = supply,
    marketCapUsd = marketCapUsd,
    volumeUsd24Hr = volumeUsd24Hr,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
    vwap24Hr = vwap24Hr,
    explorer = explorer,
)

