package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.runtime.Immutable
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import com.example.coincapapp.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
@Immutable
data class CoinState(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    @Serializable(with = BigDecimalSerializer::class)
    val priceUsd: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val changePercent24Hr: BigDecimal,
)

fun CoinEntity.toState() = CoinState(
    id = id,
    rank = rank,
    symbol = symbol,
    name = name,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr
)
