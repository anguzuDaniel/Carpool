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
            items(listOf(
                OfferedRide(
                    pickupLocation = "Kampala",
                    destination = "Entebbe",
                    seatsAvailable = 2,
                    departureTime = "10:00 AM",
                    price = "10,000 UGX"
                )
            )) { offeredRide ->
                OfferedRideItem(offeredRide)
            }
        }
    }
}

@Composable
fun OfferedRideItem(offeredRide: OfferedRide) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Pickup: ${offeredRide.pickupLocation}")
            Text(text = "Destination: ${offeredRide.destination}")
            Text(text = "Seats: ${offeredRide.seatsAvailable}")
            Text(text = "Departure Time: ${offeredRide.departureTime}")
            Text(text = "Price: ${offeredRide.price}")

            // Add buttons for edit and delete here
        }
    }
}

data class OfferedRide(
    val pickupLocation: String,
    val destination: String,
    val seatsAvailable: Int,
    val departureTime: String,
    val price: String
)