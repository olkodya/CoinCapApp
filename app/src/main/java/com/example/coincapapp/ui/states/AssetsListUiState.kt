package com.example.coincapapp.ui.states

import com.example.coincapapp.data.AssetUiModel

data class AssetsListUiState(
    val assets: List<AssetUiModel> = emptyList(),
    val status: AssetsStatus = AssetsStatus.Idle(),
    //val singleError ???
)