package com.example.coincapapp.feature.coinDetail.data

import io.ktor.client.HttpClient

class CoinWebSocket(
    private val client: HttpClient
) {
//    private var socket: DefaultClientWebSocketSession? = null
//
//    suspend fun connect(
//        coinId: String
//    ): Flow<String> = flow<String> {
//        socket = client.webSocketSession(urlString = "wss://ws.coincap.io/?" + "prices=${coinId}")
//        val list = mutableListOf<String>()
//        while (socket?.isActive == true) {
//            val model = socket?.receiveDeserialized() ?: ""
//            list.add(model)
//        }
////        emit(list)
//
//    }.flowOn()
}