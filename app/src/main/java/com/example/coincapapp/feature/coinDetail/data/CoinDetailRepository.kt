package com.example.coincapapp.feature.coinDetail.data

interface CoinDetailRepository {
    suspend fun getCoinCurrentPrice(coinId: String)
}