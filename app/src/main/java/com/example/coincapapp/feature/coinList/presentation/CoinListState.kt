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
    val priceUsd: String,
    val changePercent24Hr: String,
)

@SuppressLint("DefaultLocale")
fun CoinEntity.toState() = CoinState(
    id = id,
    rank = rank,
    symbol = symbol,
    name = name,
    priceUsd = if (priceUsd != "") {
        String.format("%.5f", priceUsd.toBigDecimal())
    } else {
        ""
    },
    changePercent24Hr = if (changePercent24Hr != "") {
        String.format("%.5f", changePercent24Hr.toBigDecimal())
    } else {
        ""
    }
)
