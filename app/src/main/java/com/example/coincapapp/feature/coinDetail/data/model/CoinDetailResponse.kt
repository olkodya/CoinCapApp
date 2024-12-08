package com.example.coincapapp.feature.coinDetail.data.model

import com.example.coincapapp.feature.coinDetail.domain.CoinDetailEntity
import com.example.coincapapp.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CoinDetailResponse(
    @Serializable(with = BigDecimalSerializer::class)
    val priceUsd: BigDecimal = BigDecimal("0.0"),
    val time: Long = 0,
)

@Serializable
data class CoinDetailListResponse(
    @Serializable
    val data: List<CoinDetailResponse> = emptyList()
)

fun CoinDetailResponse.toEntity(xValue: Int) =
    CoinDetailEntity(
        priceUsd = priceUsd,
        time = time,
        xValues = xValue
    )