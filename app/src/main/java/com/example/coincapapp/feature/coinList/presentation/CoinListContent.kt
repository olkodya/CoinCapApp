package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.coincapapp.R
import com.example.coincapapp.components.EmptyState
import com.example.coincapapp.components.ErrorState
import com.example.coincapapp.components.LoadingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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
                Text(stringResource(R.string.search_placeholder))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (coinsPagingState.itemCount == 0 &&
                coinsPagingState.loadState.refresh !is LoadState.Error &&
                coinsPagingState.loadState.refresh !is LoadState.Loading
            ) {
                item {
                    EmptyState(
                        modifier = Modifier.fillParentMaxSize(),
                        message = stringResource(R.string.empty_coins),
                    )
                }
            } else {
                items(
                    count = coinsPagingState.itemCount,
                    key = { index ->
                        coinsPagingState[index]?.id ?: index
                    }
                ) { index ->
                    val coinState = coinsPagingState[index]
                    coinState?.let { coin ->
                        CoinCard(
                            coin = coin,
                            onCoinClick = {
                                handleAction(CoinListViewModel.CoinListAction.OnCoinClicked(coin.id))
                            },
                        )
                    }
                }
            }

            coinsPagingState.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingState(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.append is LoadState.Loading -> item { LoadingPagingItem() }
                    loadState.refresh is LoadState.Error -> {
                        val e = coinsPagingState.loadState.refresh as LoadState.Error
                        item {
                            ErrorState(
                                message = e.error.localizedMessage
                                    ?: stringResource(R.string.error_string),
                                modifier = Modifier.fillParentMaxSize(),
                                onRetryClick = { retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = coinsPagingState.loadState.append as LoadState.Error
                        item {
                            ErrorPagingItem(
                                message = error.error.localizedMessage
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
private fun LoadingPagingItem() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun ErrorPagingItem(
    message: String,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            maxLines = 1,
            color = Color.Red,
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(R.string.try_again_string))
        }
    }
}

@Composable
private fun CoinCard(onCoinClick: () -> Unit, coin: CoinState) {
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
            Row {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.hashtag).format(coin.rank)
                )
                Text(
                    text = coin.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
            Text(text = stringResource(R.string.symbol).format(coin.symbol))
            Text(
                text = stringResource(R.string.price).format(
                    coin.priceUsd,
                    stringResource(R.string.usd)
                )
            )
            Text(
                text = stringResource(R.string.percent24hr)
                    .format(
                        coin.changePercent24Hr,
                        stringResource(R.string.percent),
                    )
            )
        }
    }
}


@Composable
@Preview
fun CoinCardPreview() {
    CoinCard(
        onCoinClick = {},
        coin = CoinState(
            id = "bitcoin",
            rank = "1",
            symbol = "BTC",
            name = "Bitcoin",
            priceUsd = "90387.50000",
            changePercent24Hr = "1.00000"
        )
    )
}

@Composable
@Preview(showBackground = true)
fun ErrorPagingItemPreview() {
    ErrorPagingItem(
        message = "Error",
        onClickRetry = {}
    )
}

@Composable
@Preview(showBackground = true)
fun LoadingItemPreview() {
    LoadingPagingItem()
}

@Composable
fun rememberMockCoinsPagingItems(): LazyPagingItems<CoinState> {
    val mockCoinsFlow: Flow<PagingData<CoinState>> = flowOf(
        PagingData.from(
            listOf(
                CoinState(
                    id = "Bitcoin",
                    rank = "1",
                    symbol = "BTC",
                    name = "Bitcoin",
                    priceUsd = "90387.50000",
                    changePercent24Hr = "1.0000000"
                ),
                CoinState(
                    id = "bitcoin-cash",
                    rank = "16",
                    symbol = "BCH",
                    name = "Bitcoin Cash",
                    priceUsd = "449.11382",
                    changePercent24Hr = "3.32509"
                )
            )
        )
    )
    return mockCoinsFlow.collectAsLazyPagingItems()
}

@Composable
@Preview(showBackground = true)
fun CoinListPreview() {
    CoinListContent(
        fieldState = "bitcoin",
        coinsPagingState = rememberMockCoinsPagingItems(),
    ) {
    }
}

