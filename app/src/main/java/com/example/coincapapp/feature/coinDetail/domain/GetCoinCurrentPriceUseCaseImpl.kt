package com.example.coincapapp.feature.coinDetail.domain

import com.example.coincapapp.feature.coinDetail.data.CoinDetailRealtimeClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinCurrentPriceUseCaseImpl @Inject constructor(
    private val client: CoinDetailRealtimeClient
) : GetCoinCurrentPriceUseCase {

    override suspend fun invoke(coinId: String): Flow<String> =
        client.getCoinCurrentPrice(coinId = coinId)

    override suspend fun close() = client.close()
}
