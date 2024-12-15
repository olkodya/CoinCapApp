package com.example.coincapapp.feature.coinDetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.feature.coinDetail.domain.GetCoinCurrentPriceUseCase
import com.example.coincapapp.feature.coinDetail.domain.GetCoinPriceHistoryUseCase
import com.example.coincapapp.feature.coinDetail.domain.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val currentPriceUseCase: GetCoinCurrentPriceUseCase,
    private val historyUseCase: GetCoinPriceHistoryUseCase,
) : ViewModel() {

    private val mutableCoinState: MutableStateFlow<CoinDetailScreenState> = MutableStateFlow(
        CoinDetailScreenState("", "", BigDecimal("0.0"), emptyList(), 0f)
    )
    val coinState: StateFlow<CoinDetailScreenState> = mutableCoinState.asStateFlow()

    private val mutableActions: Channel<CoinDetailEvent> = Channel()
    val action: Flow<CoinDetailEvent> = mutableActions.receiveAsFlow()


    fun handleAction(action: CoinDetailAction) {
        when (action) {
            is CoinDetailAction.OnStart -> setCoin(
                action.coinId,
                action.coinName,
                action.coinPrice
            )

            CoinDetailAction.OnBackClick -> navigateToCoinListScreen()
            is CoinDetailAction.OnRetryClick -> setCoin(
                action.coinId,
                action.coinName,
                action.coinPrice
            )

            is CoinDetailAction.OnChangePosition -> positionChanged(action.position)

            CoinDetailAction.OnEndButtonClicked -> endClicked()

            CoinDetailAction.OnStartButtonClicked -> startClicked()

            CoinDetailAction.OnMovedToStart -> setStartButtonClicked(false)

            CoinDetailAction.OnMovedToEnd -> setEndButtonClicked(false)
        }
    }

    private fun startClicked() {
        setStartButtonClicked(true)
        positionChanged(0.0f)
    }

    private fun setStartButtonClicked(isStartButtonClicked: Boolean) {
        mutableCoinState.value = coinState.value.copy(startButtonClicked = isStartButtonClicked)

    }


    private fun endClicked() {
        setEndButtonClicked(true)
        positionChanged(coinState.value.coinPriceHistory.size.toFloat())
    }

    private fun setEndButtonClicked(isEndButtonClicked: Boolean) {
        mutableCoinState.value = coinState.value.copy(endButtonClicked = isEndButtonClicked)

    }

    private fun positionChanged(position: Float) {
        mutableCoinState.value = coinState.value.copy(currentChartPosition = position)
    }

    private fun navigateToCoinListScreen() {
        viewModelScope.launch {
            mutableActions.send(CoinDetailEvent.GoBack)
        }
    }

    private fun setCoin(coinId: String, coinName: String, price: BigDecimal) {
        mutableCoinState.value =
            coinState.value.copy(
                coinId = coinId,
                coinName = coinName,
                currentPrice = price.setScale(5, RoundingMode.HALF_UP),
            )
        getCoinPriceHistory(coinId)
    }

    private fun getCoinPriceHistory(coinId: String) {
        viewModelScope.launch {
            try {
                mutableCoinState.value =
                    coinState.value.copy(
                        loading = true,
                        coinPriceHistory = emptyList(),
                        errorMessage = null
                    )
                val history =
                    historyUseCase(coinId = coinId)

                mutableCoinState.value = coinState.value.copy(
                    coinPriceHistory = coinState.value.coinPriceHistory.toMutableList()
                        .apply { addAll(history.map { it.toState() }) },
                    loading = false,
                    errorMessage = null
                )

                currentPriceUseCase(coinId).collect { price ->
                    mutableCoinState.value = coinState.value.copy(
                        currentPrice = price.priceUsd,
                        coinPriceHistory = coinState.value.coinPriceHistory.toMutableList()
                            .apply { add(price.toState(coinState.value.coinPriceHistory.size.toFloat())) },
                        loading = false,
                        errorMessage = null,
                        coinPriceIncreased = price.priceUsd > coinState.value.currentPrice
                    )
                }
            } catch (ex: Exception) {
                mutableCoinState.value = coinState.value.copy(
                    loading = false,
                    coinPriceHistory = emptyList(),
                    errorMessage = ex.message.toString()
                )
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            currentPriceUseCase.close()
        }
    }

    sealed class CoinDetailAction {
        data class OnStart(
            val coinId: String,
            val coinName: String,
            val coinPrice: BigDecimal
        ) : CoinDetailAction()

        data object OnBackClick : CoinDetailAction()
        data class OnRetryClick(
            val coinId: String,
            val coinName: String,
            val coinPrice: BigDecimal
        ) : CoinDetailAction()

        data class OnChangePosition(val position: Float) : CoinDetailAction()

        data object OnStartButtonClicked : CoinDetailAction()

        data object OnEndButtonClicked : CoinDetailAction()

        data object OnMovedToStart : CoinDetailAction()

        data object OnMovedToEnd : CoinDetailAction()

    }

    sealed class CoinDetailEvent {
        data object GoBack : CoinDetailEvent()
    }
}
