package com.example.coincapapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coincapapp.ui.actions.AssetsListAction
import com.example.coincapapp.ui.states.AssetsListUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AssetsViewModel
    : ViewModel() {

    val actions = Channel<AssetsListAction>(Channel.UNLIMITED)
    private val _state = MutableStateFlow(AssetsListUiState())
    val state = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            actions.consumeAsFlow().collect { action ->
                when (action) {
                    AssetsListAction.LoadAssets -> TODO()
                    // is AssetsListAction.SearchAssetByAssetIdOrSymbol -> TODO()
                }
            }
        }
    }

    fun loadAssets() {

    }

    fun searchAsset() {

    }

}