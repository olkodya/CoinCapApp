package com.example.coincapapp.feature.coinDetail.data

import io.ktor.client.HttpClient
import javax.inject.Inject

class CoinDetailRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : CoinDetailRepository {
    override suspend fun getCoinCurrentPrice(coinId: String) {
        TODO("Not yet implemented")
    }
}