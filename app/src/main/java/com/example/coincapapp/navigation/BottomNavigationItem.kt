package com.example.coincapapp.navigation

import com.example.coincapapp.R
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavigationItem(
    var icon: Int,
    var title: String,
) {

    @Serializable
    data object Coins :
        BottomNavigationItem(R.drawable.assets_icon, "Assets")

    @Serializable
    data object Exchanges :
        BottomNavigationItem(R.drawable.exchanges_icon, "Exchanges")
}

