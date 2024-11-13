package com.example.coincapapp.feature.coinList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.coincapapp.feature.coinList.domain.GetCoinListUseCase
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    val getCoinListUseCase: GetCoinListUseCase,
) : ViewModel() {

    private val mutableState: MutableStateFlow<CoinListState> =
        MutableStateFlow(CoinListState.InitialLoading)
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
//            val pagedFlow: Flow<PagingData<CoinEntity>> = getCoinListUseCase()
//            getCoinListUseCase()
//            println("12354132512 - " + pagedFlow.toList())


            getCoinListUseCase().collect {
                it.map {


                    println("12354132512 - " + it.toState())
                }
            }

//            mutableState.value = CoinListState.Content(response.map {
//                it.toState()
//            })


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