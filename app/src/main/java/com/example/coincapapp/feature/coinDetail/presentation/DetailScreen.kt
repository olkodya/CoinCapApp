package com.example.coincapapp.feature.coinDetail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(coinId: String) {
    Scaffold(topBar = { ExchangesTopAppBar(/*navController*/) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Button(onClick = {
//                navController.popBackStack()

            }) {
                Text("Back $coinId")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangesTopAppBar(/*navController: NavHostController*/) {
    TopAppBar(
        title = { Text(text = "Name") },
        navigationIcon = {
            IconButton(onClick = { /*navController.popBackStack()*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}
