package com.example.coincapapp.feature.coinDetail.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("")
data class RealtimeCoinPriceResponse(
    @SerialName("")
    val data: Map<String, String>
)
