package com.danoTech.carpool.ui.screens.request_ride

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun RideConfirmationScreen(
    pickupLocation: String,
    numberOfSeats: Int,
    estimatedTime: String,
    price: String,
    onConfirmRide: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Pickup Location",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(text = pickupLocation)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Number of Seats:",
            style = MaterialTheme.typography.bodySmall
        )
        Text(text = "$numberOfSeats")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Estimated Time:",
            style = MaterialTheme.typography.bodySmall
        )
        Text(text = estimatedTime)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Price:",
            style = MaterialTheme.typography.bodySmall
        )
        Text(text = price)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onConfirmRide,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Confirm Ride")
        }
    }
}

@Composable
fun FancyTab(title: String, onClick: () -> Unit, selected: Boolean) {
    Tab(selected, onClick) {
        Column(
            Modifier
                .padding(10.dp)
                .height(50.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .size(10.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color =
                        if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.background
                    )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RideConfirmScreen() {
    CarpoolTheme {
        RideConfirmationScreen(
            // Populate data based on your app logic
            pickupLocation = "Kansanga", // Replace with actual location
            numberOfSeats = 1, // Replace with user selection or default
            estimatedTime = "Calculating...",
            price = "$10.00", // Replace with actual price
            onConfirmRide = {
            }
        )
    }
}