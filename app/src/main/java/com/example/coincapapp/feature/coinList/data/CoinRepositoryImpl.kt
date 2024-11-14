package com.example.coincapapp.feature.coinList.data

import com.example.coincapapp.feature.coinList.data.model.CoinListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val client: HttpClient,
) : CoinRepository {

    @Serializable
    internal data class KtorLoadPagingCoinsParameters(
        @SerialName("search")
        val searchQuery: String,
        @SerialName("offset")
        val offset: Int,
        @SerialName("limit")
        val limit: Int,
    )


    override suspend fun loadPagingCoins(
        searchQuery: String,
        offset: Int,
        limit: Int,
    ): CoinListResponse = client
        .get("https://api.coincap.io/v2/assets") {
            parameter("search", searchQuery)
            parameter("offset", offset)
            parameter("limit", limit)
        }
        .body()
}