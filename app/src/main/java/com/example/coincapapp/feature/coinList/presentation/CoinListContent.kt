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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.coincapapp.R
import com.example.coincapapp.components.EmptyState
import com.example.coincapapp.components.ErrorState
import com.example.coincapapp.components.LoadingState

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
                        AssetCard(
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
private fun LazyListScope.CoinsList(
    coinsPagingState: LazyPagingItems<CoinState>,
    onCoinClick: (String) -> Unit,
) {
    items(
        count = coinsPagingState.itemCount,
        key = { index ->
            coinsPagingState[index]?.id ?: index
        }
    ) { index ->
        val coinState = coinsPagingState[index]
        coinState?.let {
            AssetCard(
                coin = it,
                onCoinClick = { onCoinClick(coinState.id) },
            )
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
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
//            .wrapContentWidth(Alignment.CenterHorizontally),,
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
