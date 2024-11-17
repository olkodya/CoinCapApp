package com.example.coincapapp.feature.exchangeList.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.coincapapp.R
import com.example.coincapapp.components.ErrorState
import com.example.coincapapp.components.LoadingState

@Composable
fun ExchangeListContent(value: ExchangeListState) {
    Column {
        ExchangesTopAppBar()
        when (value) {
            is ExchangeListState.Content -> ExchangeList(value)
            ExchangeListState.Error -> {
                ErrorState(
                    modifier = Modifier,
                    message = "Error",
                    onRetryClick = {}
                )
            }

            ExchangeListState.Loading -> {
                LoadingState()
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangesTopAppBar() {
    TopAppBar(title = { Text(text = "Exchanges List") })

}

@Composable
fun ExchangeList(value: ExchangeListState) {
    if (value is ExchangeListState.Content) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = value.exchanges.size, key = { id -> value.exchanges[id].id }) { index ->
                val exchange = value.exchanges[index]
                ExchangeCard(exchange)
            }

        }
    }
}

@Composable
fun ExchangeCard(exchange: ExchangeState) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {



        Column(Modifier.padding(16.dp)) {
            Row() {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(R.string.hashtag).format(exchange.rank)
                )
                Text(text = exchange.name, fontWeight = FontWeight.Bold)
            }
            Text(
                text = stringResource(R.string.volumeUsd).format(
                    exchange.volumeUsd,
                    stringResource(R.string.usd)
                )
            )
            Text(
                text = stringResource(R.string.percentTotalVolume).format(
                    exchange.percentTotalVolume,
                    stringResource(R.string.percent)
                )
            )
            Text(
                text = exchange.exchangeUrl
            )
        }
    }
}