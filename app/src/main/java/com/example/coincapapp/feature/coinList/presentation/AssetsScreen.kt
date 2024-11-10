package com.example.coincapapp.feature.coinList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.coincapapp.navigation.Routes
import com.example.coincapapp.ui.theme.CoinCapAppTheme

@Composable
fun AssetsScreen(navController: NavHostController) {
    val items = listOf(1, 3, 4, 5, 7, 8, 8, 9, 10)
    val vm: CoinListViewModel = hiltViewModel()
    LazyColumn() {
        item {
            Text(vm.state.toString())
        }
        items(items) { message ->
            AssetCard(navController, message.toString())
        }


    }

}

@Composable
private fun AssetCard(navController: NavHostController, toString: String) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                navController.navigate(Routes.AssetInfo.route)
            }
    ) {
        Row {
            AsyncImage(model = "", contentDescription = "")

            Column {
                Text(text = "Name" + toString)
                Text(text = "Rank")
                Text(text = "Price")
                Text(text = "percent24hr")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun AssetCardPreview() {
    CoinCapAppTheme {

    }
}

