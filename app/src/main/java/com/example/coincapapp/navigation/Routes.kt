package com.example.coincapapp.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Routes {
    @Serializable
    object ScreenMain

    @Serializable
    data class ScreenDetail(
        val coinId: String,
        val coinName: String,
    )
}