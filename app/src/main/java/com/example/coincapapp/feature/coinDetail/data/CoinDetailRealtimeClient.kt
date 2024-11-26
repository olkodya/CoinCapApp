package com.example.coincapapp.feature.coinDetail.data

import kotlinx.coroutines.flow.Flow

interface CoinDetailRealtimeClient {

    suspend fun getCoinCurrentPrice(coinId: String): Flow<String>
    suspend fun close()
}
