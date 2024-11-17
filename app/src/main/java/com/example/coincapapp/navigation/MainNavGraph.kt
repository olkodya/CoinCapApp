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
        startDestination = BottomNavigationItem.Coins
    ) {

        composable<BottomNavigationItem.Coins> {
            CoinListScreen(
                routeToCoinDetailScreen = {
                    rootNavHostController.navigate(
                        Routes.ScreenDetail(
                            coinId = "1"
                        )
                    )
                },
            )
        }

        composable<BottomNavigationItem.Exchanges> {
            ExchangesScreen()
        }
    }
}