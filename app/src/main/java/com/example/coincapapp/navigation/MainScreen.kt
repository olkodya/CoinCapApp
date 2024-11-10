package com.example.coincapapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(rootNavHostController: NavHostController) {

    val mainNavHostController: NavHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            MainTopAppBar(mainNavHostController, scope, snackbarHostState)
        },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    navController: NavController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    if (currentRoute == BottomNavigationItem.Assets.route) {
        var searchQuery by remember { mutableStateOf("") }
        TopAppBar(
            title = {
                Column {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Поиск") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        singleLine = true,
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                searchQuery,
                                actionLabel = "Action",
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Поиск")
                }
            }
        )
    } else if (currentRoute == BottomNavigationItem.Exchanges.route) {
        TopAppBar(
            title = {
                Text(text = "Exchanges")
            },
        )
    }
}