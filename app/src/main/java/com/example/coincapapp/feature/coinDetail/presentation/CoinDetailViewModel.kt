package com.example.coincapapp.feature.coinDetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.feature.coinDetail.domain.GetCoinCurrentPriceUseCase
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
    private val useCase: GetCoinCurrentPriceUseCase,
) : ViewModel() {

    private val mutableCoinState: MutableStateFlow<CoinDetailState> = MutableStateFlow(
        CoinDetailState("", "", BigDecimal("0.0"), emptyList())
    )
    val coinState: StateFlow<CoinDetailState> = mutableCoinState.asStateFlow()

    private val mutableActions: Channel<CoinDetailEvent> = Channel()
    val action: Flow<CoinDetailEvent> = mutableActions.receiveAsFlow()

    fun handleAction(action: CoinDetailAction) {
        when (action) {
            is CoinDetailAction.OnStart -> setCoin(action.coinId, action.coinName, action.coinPrice)
            CoinDetailAction.OnBackClick -> navigateToCoinListScreen()
        }
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
                currentPrice = price.setScale(2, RoundingMode.HALF_UP),
                coinPriceHistory = coinState.value.coinPriceHistory.toMutableList()
                    .apply { add(price.setScale(2, RoundingMode.HALF_UP)) })
        getCurrentPrice(coinId)
    }

    private fun getCoinHistory() {

    }

    private fun getCurrentPrice(coinId: String) {
        viewModelScope.launch {
            useCase(coinId).collect { price ->
                mutableCoinState.value = coinState.value.copy(
                    currentPrice = price.toBigDecimal(),
                    coinPriceHistory = coinState.value.coinPriceHistory.toMutableList()
                        .apply { add(price.toBigDecimal()) })

            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            useCase.close()
        }
    }

    sealed class CoinDetailAction {
        data class OnStart(val coinId: String, val coinName: String, val coinPrice: BigDecimal) :
            CoinDetailAction()

        data object OnBackClick : CoinDetailAction()
    }

    sealed class CoinDetailEvent {
        data object GoBack : CoinDetailEvent()
    }
}
