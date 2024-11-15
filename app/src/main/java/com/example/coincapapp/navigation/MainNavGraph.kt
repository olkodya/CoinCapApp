package com.example.coincapapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coincapapp.feature.coinList.presentation.CoinListScreen
import com.example.coincapapp.feature.exchange.presentation.ExchangesScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    rootNavHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomNavigationItem.Assets.route
    ) {

        composable(BottomNavigationItem.Assets.route) {
            CoinListScreen(
                routeToCoinDetailScreen = {
                    rootNavHostController.navigate(Routes.AssetInfo.route)
                },
            )
        }

        composable(BottomNavigationItem.Exchanges.route) {
            ExchangesScreen()
        }
    }
}