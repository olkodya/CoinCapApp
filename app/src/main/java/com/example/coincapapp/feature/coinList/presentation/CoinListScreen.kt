package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.coincapapp.ui.theme.CoinCapAppTheme

@Composable
fun CoinListScreen(onCoinClick: () -> Unit) {
    val viewModel: CoinListViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    when (state.value) {
        is CoinListState.Content -> {
            LazyColumn() {
                items((state.value as CoinListState.Content).coins) { coin ->
                    AssetCard(onCoinClick, coin)
                }

            }
        }

        is CoinListState.Error -> {
            Text("Error")
        }

        CoinListState.Loading -> {
            Text("Loading")
        }
    }


}

@Composable
private fun AssetCard(onCoinClick: () -> Unit, coin: CoinState) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable {
                onCoinClick()
            }
    ) {
        Row {
            AsyncImage(model = "", contentDescription = "")
            Column {
                Text(text = "Name " + coin.name)
                Text(text = "Rank " + coin.rank)
                Text(text = "Price " + coin.priceUsd)
                Text(text = "Symbol " + coin.symbol)
                Text(text = "percent24hr " + coin.changePercent24Hr)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun AssetCardPreview() {
    CoinCapAppTheme {

    }
}

