package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import com.example.coincapapp.ui.theme.CoinCapAppTheme

@Composable
fun CoinListScreen(onCoinClick: () -> Unit) {
    val viewModel: CoinListViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    val coins: LazyPagingItems<CoinEntity> = viewModel.pagingData.collectAsLazyPagingItems()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = coins.itemCount,
            key = { index ->
                coins[index]?.id ?: index
            }
        ) { index ->
            val coinState = coins[index]?.toState() ?: CoinState(
                id = "",
                rank = "",
                symbol = "",
                name = "",
                supply = "",
                maxSupply = "",
                marketCapUsd = "",
                volumeUsd24Hr = "",
                priceUsd = "",
                changePercent24Hr = "",
                vwap24Hr = "",
                explorer = "",
            )
            AssetCard(onCoinClick, coinState)
        }

        coins.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = coins.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage ?: "Error",
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val e = coins.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage ?: "Error",
                            onClickRetry = { retry() }
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
        Modifier
            .testTag("ProgressBarItem")
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}


@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
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

