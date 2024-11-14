package com.example.coincapapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coincapapp.R
import com.example.coincapapp.feature.coinList.presentation.CoinListViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(rootNavHostController: NavHostController) {

    val mainNavHostController: NavHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
//        topBar = {
//            MainTopAppBar(mainNavHostController, scope, snackbarHostState, viewModel)
//        },
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
    snackbarHostState: SnackbarHostState,
    viewModel: CoinListViewModel
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
                        onValueChange = {
                            searchQuery = it
                            viewModel.handleAction(
                                CoinListViewModel.CoinListAction.OnSearchFieldEdited(
                                    it
                                )
                            )
                        },
                        placeholder = { Text(stringResource(R.string.search_string)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        singleLine = true,
                    )
                }
            },
        )
    } else if (currentRoute == BottomNavigationItem.Exchanges.route) {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.exchanges_tool_bar_title))
            },
        )
    }
}