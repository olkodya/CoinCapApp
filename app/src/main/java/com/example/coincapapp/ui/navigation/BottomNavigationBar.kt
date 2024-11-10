package com.example.coincapapp.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController, modifier: Modifier
) {

    val screens = listOf(
        BottomNavigationItem.Assets, BottomNavigationItem.Exchanges
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.Transparent,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.title) },
                selected = currentRoute == screen.route || (currentRoute == Routes.AssetInfo.route && screen.route == BottomNavigationItem.Assets.route),
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                            launchSingleTop = true
                        //    restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Black,
                    selectedTextColor = Color.Blue,
                    unselectedIconColor = Color.Black,
                    selectedIconColor = Color.Blue
                )
            )
        }
    }
}
