package com.example.coincapapp.feature.coinDetail.domain

import com.example.coincapapp.feature.coinDetail.presentation.CoinDetailState
import java.math.BigDecimal

data class CoinDetailEntity(
    val priceUsd: BigDecimal,
    val time: Long,
)

fun CoinDetailEntity.toState() =
    CoinDetailState(
        priceUsd = priceUsd,
        time = time
    )