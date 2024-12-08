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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.coincapapp.R
import com.example.coincapapp.components.ErrorState
import com.example.coincapapp.components.LoadingState
import com.example.coincapapp.utils.DecimalValueFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import java.math.BigDecimal

@Composable
fun CoinDetailContent(
    state: CoinDetailScreenState,
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
                        Chart2(state)
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
fun Chart(state: CoinDetailScreenState) {
    val lineChartData = listOf(LineChartData(points = state.coinPriceHistory.map {
        LineChartData.Point(it.priceUsd.toFloat(), "")
    }, lineDrawer = SolidLineDrawer()))
    LineChart(
        modifier = Modifier
            .fillMaxSize()
            .width(1600.dp)
            .padding(horizontal = 8.dp, vertical = 32.dp),
        linesChartData = lineChartData,
        horizontalOffset = 5f,
    )
}


@Composable
fun Chart2(state: CoinDetailScreenState) {
    LineGraph(
        yData = state.coinPriceHistory.map { it.priceUsd.toFloat() },
        xData = (0..state.coinPriceHistory.size).toList().map { it.toFloat() },
        dataLabel = "${state.coinName} price",
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 32.dp),

        )
}


@Composable
fun LineGraph(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier = Modifier
) {

    var currentXPosition by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val chart = LineChart(context)
            val entries: List<Entry> =
                xData.zip(yData) { x, y -> Entry(x, y) }
            println(entries)
            println(entries.size)
            val dataSet = LineDataSet(entries, dataLabel).apply {
                lineWidth = 4f
                circleRadius = 6f
            }
            chart.data = LineData(dataSet)
            currentXPosition = if (xData.size > 3) {
                xData.size - 3.toFloat()
            } else {
                0f
            }
            chart.setTouchEnabled(true)
            chart.isDragEnabled = true
            chart.isScaleXEnabled = false
            chart.isScaleYEnabled = false
            chart.invalidate()
            chart
        },
        update = { view ->
            val entries: List<Entry> =
                xData.zip(yData) { x, y -> Entry(x, y) }
            val dataSet = LineDataSet(entries, dataLabel).apply {
                lineWidth = 4f
                valueTextSize = 14f
                circleRadius = 6f
                valueFormatter = DecimalValueFormatter()
            }
            view.data = LineData(dataSet)
            view.setVisibleXRange(2f, 3f)
            currentXPosition = if (xData.size > 3) {
                xData.size - 3.toFloat()
            } else {
                0f
            }
            view.moveViewToX(currentXPosition)
            view.invalidate()
        }
    )
}


@Composable
fun CandleCHart(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier = Modifier
) {
//
//    var currentXPosition by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
//    AndroidView(
//        modifier = modifier.fillMaxSize(),
//        factory = { context ->
//            val chart = CandleStickChart(context)  // Initialise the chart
//            val entries: List<CandleEntry> =
//                xData.zip(yData) { x, y -> CandleEntry(x, y) }
//            // Convert the x and y data into entries
//            val dataSet = CandleDataSet(entries, dataLabel)  // Create a dataset of entries
//
//
//
//
//
//            chart.data = CandleEntry(dataSet, )  // Pass the dataset to the chart
//            chart.moveViewToX(xData[xData.size - 10])
//
//            chart.setTouchEnabled(true)
//            chart.isDragEnabled = true
//            chart.isScaleXEnabled = false
//            chart.isScaleYEnabled = false
//            chart.invalidate()
//            chart
//
//        },
//        update = { view ->
//            val entries: List<Entry> =
//                xData.zip(yData) { x, y -> Entry(x, y) }
//            val dataSet = LineDataSet(entries, dataLabel)
//            view.data = LineData(dataSet)
//            view.setVisibleXRange(10F, 15f);
//            if (xData.size > 15) {
//                currentXPosition =
//                    xData.size - 15.toFloat() // Устанавливаем положение на последние 20 точек
//            } else {
//                currentXPosition = 0f // Если точек меньше 20, показываем все
//            }
//            view.moveViewToX(currentXPosition)
//            view.invalidate()
//        }
//    )
}

@Composable
@Preview(showBackground = true)
fun CoinDetailChartPreview() {
    val state = CoinDetailScreenState(
        coinId = "bitcoin",
        coinName = "Bitcoin",
        coinPriceHistory = listOf(
            CoinDetailState(BigDecimal("95000.43"), 0),
            CoinDetailState(BigDecimal("95000.43"), 0),
            CoinDetailState(BigDecimal("95000.43"), 0),
            CoinDetailState(BigDecimal("95000.43"), 0),
            CoinDetailState(BigDecimal("95000.43"), 0)
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
    val state = CoinDetailScreenState(
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
    val state = CoinDetailScreenState(
        coinId = "bitcoin",
        coinName = "Bitcoin",
        coinPriceHistory = emptyList(),
        currentPrice = BigDecimal("95000.0"),
        loading = true,
        errorMessage = null
    )
    CoinDetailContent(state) {}
}

