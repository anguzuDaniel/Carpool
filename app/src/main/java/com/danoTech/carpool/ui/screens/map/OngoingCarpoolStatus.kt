package com.danoTech.carpool.ui.screens.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OngoingCarpoolStatus(
    pickupLocation: String,
    destination: String,
    onCancelCarpool: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You are currently in a carpool", style = MaterialTheme.typography.titleSmall)
        Text(text = "From: $pickupLocation")
        Text(text = "To: $destination")

        Button(
            onClick = { onCancelCarpool() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Cancel Carpool")
        }
    }
}
