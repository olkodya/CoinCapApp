package com.example.coincapapp.ui.states

sealed interface AssetsStatus {
    data class Idle(val finished: Boolean = false) : AssetsStatus
    data object Loading : AssetsStatus
    //data object
}