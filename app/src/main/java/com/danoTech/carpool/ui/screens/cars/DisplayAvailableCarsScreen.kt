package com.danoTech.carpool.ui.screens.cars

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danoTech.carpool.ui.PageWithBackButton

@Composable
fun DisplayAvailableCarsScreen(
    onBack: () -> Unit,
    destination: String,
    onCarClick: (String) -> Unit
) {
    PageWithBackButton(
        title = "Available Cars",
        onBackButtonClicked = onBack
    ) { innerPadding ->
        DisplayCarPoolList(
            modifier = Modifier.padding(innerPadding),
            destination = destination,
            onCarClick = onCarClick
        )
    }
}