package com.example.coincapapp.feature.coinDetail.data.model

import com.example.coincapapp.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CoinDetailResponse(
    @Serializable(with = BigDecimalSerializer::class)
    val priceUsd: BigDecimal = BigDecimal("0.0"),
    val time: String = "",
)

@Serializable
data class CoinDetailListResponse(
    @Serializable
    val data: List<CoinDetailResponse> = emptyList()
)
