package com.danoTech.carpool.ui.screens.request_ride

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.ui.screens.offer_ride.OfferedRideUiState

@Composable
fun OfferPoolScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Offered Rides",
            style = MaterialTheme.typography.bodySmall
        )

        // Replace with a list of your offered rides
        LazyColumn {
//            items(listOf(
//
//            )) { offeredRide ->
//                OfferedRideItem(offeredRide)
//            }
        }
    }
}

@Composable
fun OfferedRideItem(offeredRideUiState: OfferedRideUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Pickup: ${offeredRideUiState.pickupLocation}")
            Text(text = "Destination: ${offeredRideUiState.destination}")
            Text(text = "Seats: ${offeredRideUiState.seatsAvailable}")
            Text(text = "Departure Time: ${offeredRideUiState.departureTime}")
            Text(text = "Price: ${offeredRideUiState.price}")

            // Add buttons for edit and delete here
        }
    }
}

