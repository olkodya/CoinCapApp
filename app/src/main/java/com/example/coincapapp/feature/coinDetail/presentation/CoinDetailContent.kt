package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CoinDetailContent(
    state: CoinDetailState,
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit,
    currentPrice: String
) {
    Scaffold(topBar = {
        ExchangesTopAppBar(
            coinName = state.coinName,
            handleAction
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

//            Button(onClick = {
//
//            }) {
//                Text("Back ${state.coinId}")
//            }$


            Column {
                Text("Current price of ${state.coinId} = ${state.currentPrice}}")
                Text("Price history:  ${state.coinPriceHistory}")
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangesTopAppBar(
    coinName: String,
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit,
) {
    TopAppBar(
        title = { Text(text = coinName) },
        navigationIcon = {
            IconButton(onClick = { handleAction(CoinDetailViewModel.CoinDetailAction.OnBackClick) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}
