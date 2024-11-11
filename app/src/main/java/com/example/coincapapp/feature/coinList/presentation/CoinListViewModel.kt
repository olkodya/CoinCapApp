package com.example.coincapapp.feature.coinList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.feature.coinList.domain.GetCoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    val getCoinListUseCase: GetCoinListUseCase,
) : ViewModel() {

    private val mutableState: MutableStateFlow<CoinListState> =
        MutableStateFlow(CoinListState.Loading)
    val state: StateFlow<CoinListState> = mutableState.asStateFlow()

    private val mutableActions: Channel<CoinListEvent> = Channel()
    val action: Flow<CoinListEvent> = mutableActions.receiveAsFlow()

    init {
        loadAssets()
    }

    fun handleAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnSearchFieldSelected -> {

            }

            is CoinListAction.OnCoinClicked -> {

            }
        }
    }

    fun loadAssets() {
        viewModelScope.launch {
            val response = getCoinListUseCase("1", 1, 1)
            println("12354132512 - " + response)
            mutableState.value = CoinListState.Content(response.map {
                it.toState()
            })

        }
    }

    fun searchAsset() {

    }


    sealed class CoinListAction {
        data class OnSearchFieldSelected(val string: String) : CoinListAction()
        data class OnCoinClicked(val coinId: String) : CoinListAction()
    }

    sealed class CoinListEvent {
        data class NavigateToCoinDetail(val coinId: String) : CoinListEvent()
    }
}