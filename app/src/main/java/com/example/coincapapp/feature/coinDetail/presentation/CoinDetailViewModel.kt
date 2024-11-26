package com.example.coincapapp.feature.coinDetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.feature.coinDetail.data.CoinDetailRealtimeClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinDetailRealtimeClient: CoinDetailRealtimeClient
) : ViewModel() {
    private val mutableCoinState: MutableStateFlow<CoinDetailState> = MutableStateFlow(
        CoinDetailState("", "", "", emptyList())
    )
    val coinState: StateFlow<CoinDetailState> = mutableCoinState.asStateFlow()

    private val mutableActions: Channel<CoinDetailEvent> = Channel()
    val action: Flow<CoinDetailEvent> = mutableActions.receiveAsFlow()

    val state: StateFlow<String> = coinDetailRealtimeClient
        .getCoinCurrentPrice(coinId = coinState.value.coinId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")


    fun handleAction(action: CoinDetailAction) {
        when (action) {
            is CoinDetailAction.OnStart -> setCoin(action.coinId, action.coinName)
            CoinDetailAction.OnBackClick -> navigateToCoinListScreen()
        }
    }

    private fun navigateToCoinListScreen() {
        viewModelScope.launch {
            mutableActions.send(CoinDetailEvent.GoBack)
        }
    }

    private fun setCoin(coinId: String, coinName: String) {
        mutableCoinState.value = coinState.value.copy(coinId = coinId, coinName = coinName)
        getCurrentPrice(coinId)
    }

    private fun getCoinHistory() {

    }

    private fun getCurrentPrice(coinId: String) {
        viewModelScope.launch {
            coinDetailRealtimeClient.getCoinCurrentPrice(coinId = coinId).collect { price ->
                mutableCoinState.value = coinState.value.copy(
                    currentPrice = price,
                    coinPriceHistory = coinState.value.coinPriceHistory.toMutableList()
                        .apply { add(price) })

            }
        }

    }

    sealed class CoinDetailAction {
        data class OnStart(val coinId: String, val coinName: String) : CoinDetailAction()
        data object OnBackClick : CoinDetailAction()
    }

    sealed class CoinDetailEvent {
        data object GoBack : CoinDetailEvent()

    }
}