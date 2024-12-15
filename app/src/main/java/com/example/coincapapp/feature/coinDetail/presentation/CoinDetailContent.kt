package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.coincapapp.R
import com.example.coincapapp.components.ErrorState
import com.example.coincapapp.components.LoadingState
import com.example.coincapapp.ui.theme.DecreaseColor
import com.example.coincapapp.ui.theme.IncreaseColor
import com.example.coincapapp.utils.DecimalValueFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.current_price, state.currentPrice),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (state.coinPriceIncreased) {
                        IncreaseColor
                    } else {
                        DecreaseColor
                    }
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
                        ChartNavigationButtons(handleAction)
                        Chart(state, handleAction)
                    }
                }
            }
        }
    }
}


@Composable
fun ChartNavigationButtons(
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = {
                handleAction(CoinDetailViewModel.CoinDetailAction.OnStartButtonClicked)
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Go to start of chart"
            )
        }
        IconButton(
            onClick = {
                handleAction(CoinDetailViewModel.CoinDetailAction.OnEndButtonClicked)
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_forward_24),
                contentDescription = "Go to end of chart"
            )
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
fun Chart(
    state: CoinDetailScreenState,
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit
) {
    LineGraph(
        data = ArrayList(state.coinPriceHistory.map { it.data }),
        dataLabel = "${state.coinName} price",
        xLabels = ArrayList(state.coinPriceHistory.map { it.time }),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        position = state.currentChartPosition,
        handleAction = handleAction,
        isStartClicked = state.startButtonClicked,
        isEndClicked = state.endButtonClicked,
    )
}

@Composable
fun LineGraph(
    data: List<Entry>,
    xLabels: List<String>,
    dataLabel: String,
    modifier: Modifier = Modifier,
    position: Float,
    handleAction: (CoinDetailViewModel.CoinDetailAction) -> Unit,
    isStartClicked: Boolean,
    isEndClicked: Boolean,
) {
    val color = MaterialTheme.colorScheme.primary.toArgb()
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface.toArgb()
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            val chart = LineChart(context)
            setupChart(chart, data, dataLabel, color, onSurfaceColor, xLabels)
            val visibleEntries = chart.highestVisibleX
            if (position != 0f) {
                chart.moveViewToX(position - 3)
            } else {
                chart.moveViewToX(data.last().x)
            }
            handleAction(
                CoinDetailViewModel.CoinDetailAction.OnChangePosition(visibleEntries)
            )
            chart.invalidate()
            chart
        },
        update = { view ->
            setupChart(view, data, dataLabel, color, onSurfaceColor, xLabels)
            val visibleEntries = view.highestVisibleX
            val isAtEnd = visibleEntries >= data.size - 3

            if (isStartClicked) {
                view.moveViewToX(0F)
                handleAction(CoinDetailViewModel.CoinDetailAction.OnMovedToStart)
            }

            if (isEndClicked) {
                view.moveViewToX(data.last().x)
                handleAction(CoinDetailViewModel.CoinDetailAction.OnMovedToEnd)
            }

            if (isAtEnd && data.isNotEmpty() && !isStartClicked && !isEndClicked) {
                view.moveViewToX(data.last().x)
            }

            if (!isStartClicked && !isEndClicked && !isAtEnd)
                handleAction(
                    CoinDetailViewModel.CoinDetailAction.OnChangePosition(visibleEntries)
                )
            view.invalidate()
        }
    )
}

fun setupChart(
    chart: LineChart,
    data: List<Entry>,
    dataLabel: String,
    chartColor: Int,
    onSurfaceColor: Int,
    xLabels: List<String>,
) {
    val dataSet = LineDataSet(data, dataLabel).apply {
        lineWidth = 4f
        circleRadius = 6f
        color = chartColor
        setCircleColor(chartColor)
        valueTextSize = 14f
        valueTextColor = onSurfaceColor
        valueFormatter = DecimalValueFormatter()
    }
    chart.data = LineData(dataSet)
    chart.setExtraOffsets(10f, 10f, 10f, 10f)
    chart.setTouchEnabled(true)
    chart.isDragEnabled = true
    chart.description.isEnabled = false
    chart.isScaleXEnabled = false
    chart.axisLeft.isEnabled = false
    chart.isScaleYEnabled = false
    chart.xAxis.setDrawGridLines(false)
    chart.xAxis.axisLineColor = onSurfaceColor
    chart.xAxis.textColor = onSurfaceColor
    chart.xAxis.valueFormatter = IndexAxisValueFormatter(xLabels)
    chart.legend.isEnabled = false

    chart.xAxis.apply {
        textSize = 12f
        position = XAxis.XAxisPosition.BOTTOM
    }

    chart.axisRight.apply {
        textSize = 12f
        spaceMax = 40f
    }
    chart.axisRight.setDrawGridLines(true)
    chart.axisRight.axisLineColor = onSurfaceColor
    chart.axisRight.textColor = onSurfaceColor
    chart.axisRight.gridColor = onSurfaceColor
    chart.setVisibleXRange(3f, 3f)
}

@Composable
@Preview(showBackground = true)
fun CoinDetailChartPreview() {
    val state = CoinDetailScreenState(
        coinId = "bitcoin",
        coinName = "Bitcoin",
        coinPriceHistory = listOf(
            CoinDetailState(data = Entry(0F, 95000.43F), time = "00:38:00"),
            CoinDetailState(data = Entry(1F, 95000.41F), time = "00:38:05"),
            CoinDetailState(data = Entry(2F, 95001.43F), time = "00:38:10"),
            CoinDetailState(data = Entry(3F, 95001.02F), time = "00:38:15"),
        ),
        currentPrice = BigDecimal("95000.0"),
        loading = false,
        errorMessage = null,
        currentChartPosition = 3f
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
        errorMessage = "Error",
        currentChartPosition = 0f
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
        errorMessage = null,
        currentChartPosition = 0f
    )
    CoinDetailContent(state) {}
}


