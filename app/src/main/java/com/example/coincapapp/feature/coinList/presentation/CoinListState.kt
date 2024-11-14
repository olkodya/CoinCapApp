package com.example.coincapapp.feature.coinList.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity

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

@SuppressLint("DefaultLocale")
fun CoinEntity.toState() = CoinState(
    id = id,
    rank = rank,
    symbol = symbol,
    name = name,
    supply = supply,
    maxSupply = maxSupply,
    marketCapUsd = marketCapUsd,
    volumeUsd24Hr = volumeUsd24Hr,
    priceUsd = String.format("%.3f", priceUsd.toBigDecimal()),
    changePercent24Hr = String.format("%.3f", priceUsd.toBigDecimal()),
    vwap24Hr = vwap24Hr,
    explorer = explorer,
)

//fun CoinState.toEntity() = CoinEntity(
//    id = id,
//    rank = rank,
//    symbol = symbol,
//    name = name,
//    supply = supply,
//    maxSupply = maxSupply,
//    marketCapUsd = marketCapUsd,
//    volumeUsd24Hr = volumeUsd24Hr,
//    priceUsd = priceUsd,
//    changePercent24Hr = changePercent24Hr,
//    vwap24Hr = vwap24Hr,
//    explorer = explorer
//)
