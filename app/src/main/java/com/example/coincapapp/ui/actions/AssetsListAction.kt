package com.example.coincapapp.ui.actions

sealed interface AssetsListAction {
    data object LoadAssets : AssetsListAction
   // data class SearchAssetByAssetIdOrSymbol(val name: String) : AssetsListAction
    //data class NavigateToAsset(val assetId: String):AssetsListEvent
}