package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coincapapp.R
import com.example.coincapapp.components.ErrorState
import com.example.coincapapp.components.LoadingState
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import java.math.BigDecimal

@Composable
fun CoinDetailContent(
    state: CoinDetailState,
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit,
) {
    Scaffold(topBar = {
        CoinDetailTopAppBar(
            coinName = state.coinName, handleAction
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text(
                    stringResource(R.string.current_price, state.currentPrice),
                    fontWeight = FontWeight.Bold
                )
                when {

                    state.isLoading -> {
                        LoadingState(Modifier.fillMaxSize())
                    }

                    state.hasError != null -> {
                        (if (state.hasError.equals("null")) stringResource(R.string.error_string) else state.hasError)?.let {
                            ErrorState(
                                Modifier.fillMaxSize(),
                                message = it,
                            ) {
                                handleAction(
                                    CoinDetailViewModel.CoinDetailAction.OnRetryClick(
                                        state.coinId,
                                        state.coinName,
                                        state.currentPrice
                                    )
                                )
                            }
                        }
                    }

                    state.coinPriceHistory.isNotEmpty() -> {
                        Chart(state)
                    }
                }

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
    TopAppBar(title = { Text(text = coinName) }, navigationIcon = {
        IconButton(onClick = { handleAction(CoinDetailViewModel.CoinDetailAction.OnBackClick) }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
            )
        }
    })
}

@Composable
fun Chart(state: CoinDetailState) {
    val lineChartData = listOf(LineChartData(points = state.coinPriceHistory.map {
        LineChartData.Point(it.toFloat(), "")
    }, lineDrawer = SolidLineDrawer()))
    LineChart(
        modifier = Modifier
            .fillMaxSize()
//            .horizontalScroll(rememberScrollState())
            .width(1600.dp)
            .padding(horizontal = 8.dp, vertical = 32.dp),
        linesChartData = lineChartData,
        horizontalOffset = 5f,
    )
}

@Composable
@Preview(showBackground = true)
fun CoinDetailChartPreview() {
    val state = CoinDetailState(
        coinId = "bitcoin",
        coinName = "Bitcoin",
        coinPriceHistory = listOf(
            BigDecimal("95000.46"),
            BigDecimal("95000.43"),
            BigDecimal("95000.42"),
            BigDecimal("95001.42"),
            BigDecimal("95002.38"),
            BigDecimal("95000.41"),
            BigDecimal("95000.42"),
            BigDecimal("95000.42"),
        ),
        currentPrice = BigDecimal("95000.0"),
        loading = false,
        errorMessage = null
    )
    CoinDetailContent(state) {}
}

@Composable
@Preview(showBackground = true)
fun CoinDetailErrorStatePreview() {
    val state = CoinDetailState(
        coinId = "bitcoin",
        coinName = "Bitcoin",
        coinPriceHistory = emptyList(),
        currentPrice = BigDecimal("95000.0"),
        loading = false,
        errorMessage = "Error"
    )
    CoinDetailContent(state) {}
}

@Composable
@Preview(showBackground = true)
fun CoinDetailLoadingStatePreview() {
    val state = CoinDetailState(
        coinId = "bitcoin",
        coinName = "Bitcoin",
        coinPriceHistory = emptyList(),
        currentPrice = BigDecimal("95000.0"),
        loading = true,
        errorMessage = null
    )
    CoinDetailContent(state) {}
}
