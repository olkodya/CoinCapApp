package com.example.coincapapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.coincapapp.ui.navigation.Routes

@Composable
fun AssetCard(navController: NavHostController, toString: String) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                navController.navigate(Routes.AssetInfo.route)
            }
    ) {
        Column {
            Text("Test")
            Text("Test")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetCardPreview() {

}