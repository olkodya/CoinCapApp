package com.example.coincapapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coincapapp.ui.navigation.BottomNavigationBar
import com.example.coincapapp.ui.navigation.MainNavGraph

@Composable
fun MainScreen(rootNavHostController: NavHostController) {
    val mainNavHostController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = mainNavHostController,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavGraph(mainNavHostController, rootNavHostController)
        }
    }
}