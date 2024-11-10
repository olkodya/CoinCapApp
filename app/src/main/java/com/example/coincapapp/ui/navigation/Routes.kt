package com.example.coincapapp.ui.navigation

sealed class Routes(val route: String) {
    object Main : Routes("main")
    object AssetInfo : Routes("asset_info")
}