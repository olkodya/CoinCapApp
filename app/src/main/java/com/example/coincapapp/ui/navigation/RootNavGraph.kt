package com.example.coincapapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coincapapp.ui.screens.DetailScreen
import com.example.coincapapp.ui.screens.MainScreen

@Composable
fun RootNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController, startDestination = Routes.Main.route,
    ) {

        composable(Routes.Main.route) {
            MainScreen(navHostController)
        }

        composable(Routes.AssetInfo.route) {
            DetailScreen(navHostController)
        }
    }
}