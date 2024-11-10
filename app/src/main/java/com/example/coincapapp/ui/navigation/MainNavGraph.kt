package com.example.coincapapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coincapapp.ui.screens.AssetsScreen
import com.example.coincapapp.ui.screens.ExchangesScreen

@Composable
fun MainNavGraph(navHostController: NavHostController, rootNavHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavigationItem.Assets.route
    ) {
        composable(BottomNavigationItem.Assets.route) {
            AssetsScreen(navController = rootNavHostController)
        }

        composable(BottomNavigationItem.Exchanges.route) {
            ExchangesScreen()
        }

    }

}