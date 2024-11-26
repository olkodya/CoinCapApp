package com.example.coincapapp.feature.coinDetail.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RealtimeCoinPriceResponse(
    @SerialName("bitcoin")
    val bitcoin: String,
)