package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer

@Composable
fun CoinDetailContent(
    state: CoinDetailState,
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit,
) {
    Scaffold(topBar = {
        CoinDetailTopAppBar(
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
            Column(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text("Current price: ${state.currentPrice} $", fontWeight = FontWeight.Bold)
//                Text("Price history:  ${state.coinPriceHistory}")
                Chart(state)
            }

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailTopAppBar(
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


@Composable
fun Chart(state: CoinDetailState) {
    val lineChartData = listOf(LineChartData(points = state.coinPriceHistory.map {
        LineChartData.Point(it.toFloat(), "")
    }, lineDrawer = SolidLineDrawer()))
    LineChart(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
            .width(1600.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        linesChartData = lineChartData,
        horizontalOffset = 5f,
    )
}
