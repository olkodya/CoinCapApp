package com.example.coincapapp.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AssetsScreen(navController: NavHostController) {
    Text(text = "Assets")
    val items = listOf(1, 3, 4, 5, 7, 8, 8, 9, 10)
    LazyColumn() {
        items(items) { message ->
            AssetCard(navController, message.toString())
        }

    }

}