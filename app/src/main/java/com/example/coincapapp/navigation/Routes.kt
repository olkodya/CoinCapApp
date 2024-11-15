package com.example.coincapapp.navigation

sealed class Routes(val route: String) {
    data object Main : Routes("main")
    data object AssetInfo : Routes("asset_info")
}
