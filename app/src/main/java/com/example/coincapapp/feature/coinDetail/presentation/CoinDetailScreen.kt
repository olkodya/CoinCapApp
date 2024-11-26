package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CoinDetailScreen(
    coinId: String,
    coinName: String,
    routeBackStack: () -> Unit
) {
    val viewModel: CoinDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.handleAction(
            CoinDetailViewModel.CoinDetailAction.OnStart(
                coinId = coinId,
                coinName = coinName
            )
        )
        viewModel.action.collect { action ->
            when (action) {
                CoinDetailViewModel.CoinDetailEvent.GoBack -> routeBackStack()
            }
        }
    }

    CoinDetailContent(
        viewModel.coinState.collectAsState().value,
        { viewModel.handleAction(it) },
        viewModel.state.collectAsState().value,
    )
}