package com.danoTech.carpool.ui.screens.cars

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.ui.PageWithBackButton

@Composable
fun CarDisplayScreen(
    carId: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onChatClick: (String) -> Unit = {},
    viewModel: CarViewModel = hiltViewModel()
) {
    val carState = viewModel.cars.collectAsState().value
    val car = carState.find { it.id == carId }

    PageWithBackButton(title = "Car Details", onBackButtonClicked = onBackClicked) { innerPadding ->
        if (car != null) {
            CarDetailsScreen(
                car = car,
                modifier = modifier.padding(innerPadding),
                onChatClick = onChatClick
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Car not found",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}