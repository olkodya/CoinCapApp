package com.example.coincapapp.feature.exchangeList.presentation

import androidx.compose.runtime.Immutable
import java.math.BigDecimal


@Immutable
sealed class ExchangeListState {
    @Immutable
    data class Content(
        val exchanges: List<ExchangeState> = emptyList()
    ) : ExchangeListState()

    data object Loading : ExchangeListState()

    data object Error : ExchangeListState()
}

data class ExchangeState(
    val id: String,
    val name: String,
    val rank: String,
    val percentTotalVolume: BigDecimal,
    val volumeUsd: BigDecimal,
    val exchangeUrl: String,
)