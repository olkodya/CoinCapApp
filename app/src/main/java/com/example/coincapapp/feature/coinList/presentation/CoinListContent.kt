package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.coincapapp.R

@Composable
fun CoinListContent(
    fieldState: String,
    coinsPagingState: LazyPagingItems<CoinState>,
    handleAction: (CoinListViewModel.CoinListAction) -> Unit,
) {
    Column {
        TextField(
            value = fieldState,
            onValueChange = { query ->
                handleAction(CoinListViewModel.CoinListAction.OnSearchFieldEdited(query))
            },
            placeholder = {
                Text(stringResource(R.string.search_string))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                count = coinsPagingState.itemCount,
                key = { index ->
                    coinsPagingState[index]?.id ?: index
                }
            ) { index ->
                val coinState = coinsPagingState[index] /*?: CoinState(
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
                )*/
                coinState?.let {
                    AssetCard(
                        coin = it,
                        onCoinClick = {
                            handleAction(CoinListViewModel.CoinListAction.OnCoinClicked(coinState.id))
                        },
                    )
                }
            }

            coinsPagingState.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem() }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e = coinsPagingState.loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage
                                    ?: stringResource(R.string.error_string),
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val e = coinsPagingState.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage
                                    ?: stringResource(R.string.error_string),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingItem() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
        )
    }

}

@Composable
private fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//        Text(
//            text = message,
//            maxLines = 1,
//            modifier = Modifier.weight(1f),
//            color = Color.Red,
//
//        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(R.string.try_again_string))
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
        Column(Modifier.padding(16.dp)) {
            Text(text = "Name " + coin.name)
            Text(text = "Rank " + coin.rank)
            Text(text = "Price " + coin.priceUsd)
            Text(text = "Symbol " + coin.symbol)
            Text(text = "percent24hr " + coin.changePercent24Hr)
        }
    }
}
