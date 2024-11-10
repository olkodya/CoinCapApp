package com.example.coincapapp.ui.navigation

import com.example.coincapapp.R

sealed class BottomNavigationItem(var route: String, var icon: Int, var title: String) {
    data object Assets : BottomNavigationItem("assets_root", R.drawable.assets_icon, "Assets")
    data object Exchanges :
        BottomNavigationItem("exchanges_root", R.drawable.exchanges_icon, "Exchanges")
}