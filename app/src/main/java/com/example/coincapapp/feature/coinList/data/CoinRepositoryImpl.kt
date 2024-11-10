package com.example.coincapapp.feature.coinList.data

import com.example.coincapapp.feature.coinList.data.model.CoinListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CoinRepositoryImpl(
    private val client: HttpClient,
) : CoinRepository {

    override suspend fun loadPagingCoins(
        searchQuery: String,
        offset: Int,
        limit: Int,
    ): CoinListResponse = client
        .get("https://api.coincap.io/v2/assets")
        .body()
}