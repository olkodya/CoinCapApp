package com.example.coincapapp.feature.coinDetail.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CoinDetailRealtimeClientImpl @Inject constructor(
    private val client: HttpClient
) : CoinDetailRealtimeClient {

    private var session: WebSocketSession? = null

    override suspend fun getCoinCurrentPrice(coinId: String): Flow<String> {
        return flow {
            session = client.webSocketSession {
                url("wss://ws.coincap.io/prices?assets=$coinId")
            }
            val currentPrices = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull { Json.decodeFromString<Map<String, String>>(it.readText()) }
            emitAll(currentPrices.map { it[coinId].toString() })
        }
    }

    override suspend fun close() {
        session?.close()
        session = null
    }
}
