package com.example.coincapapp.feature.exchangeList.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ExchangeListScreen() {
    val viewModel: ExchangeListViewModel = hiltViewModel()

    LaunchedEffect(Unit) {

    }

    ExchangeListContent(viewModel.exchangeListState.collectAsState().value)
}
