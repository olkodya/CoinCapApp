package com.example.coincapapp.feature.coinList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coincapapp.feature.coinList.domain.GetCoinListUseCase
import com.example.coincapapp.feature.coinList.domain.entities.CoinEntity
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

    private val _pagingData: MutableStateFlow<PagingData<CoinEntity>> =
        MutableStateFlow(PagingData.empty())
    val pagingData: StateFlow<PagingData<CoinEntity>> = _pagingData.asStateFlow()
    private val mutableActions: Channel<CoinListEvent> = Channel()

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    val action: Flow<CoinListEvent> = mutableActions.receiveAsFlow()

    init {
        viewModelScope.launch {
//            val query = "bitcoinpos"
//            repeat(10) {
            loadAssets("")
//                delay(1_000)
        }
//        }
    }

    fun handleAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnSearchFieldEdited -> {
                loadAssets(action.query)
            }

            is CoinListAction.OnCoinClicked -> {

            }
        }
    }

    private fun loadAssets(searchQuery: String) {
        viewModelScope.launch {

            getCoinListUseCase(searchQuery)
                .cachedIn(viewModelScope)
                .collect {
                    _pagingData.value = it
                }
        }
    }

    fun searchAsset() {

    }


    sealed class CoinListAction {
        data class OnSearchFieldEdited(val query: String) : CoinListAction()
        data class OnCoinClicked(val coinId: String) : CoinListAction()
    }

    sealed class CoinListEvent {
        data class NavigateToCoinDetail(val coinId: String) : CoinListEvent()
    }
}