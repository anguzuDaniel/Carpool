package com.danoTech.carpool.ui.screens.offer_ride

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate

@Composable
fun AddRideScreen(
    modifier: Modifier = Modifier,
    viewModel: RideOfferViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 60.dp)
    ) {
        Text(
            text = "Offer a Ride",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = uiState.pickupLocation,
            onValueChange = { viewModel.addPickUpLocation(it) },
            label = { Text("Pickup Location") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.destination,
            onValueChange = { viewModel.addDestination(it) },
            label = { Text("Destination") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = uiState.seatsAvailable.toString(),
            onValueChange = { uiState.seatsAvailable = it.toIntOrNull() ?: 0 },
            label = { Text("Seats Available") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        DatePickerDocked(
            departureTime = uiState.date,
            onDepartureTimeClicked = {
                viewModel.updateSelectedDate(LocalDate.parse(it))
            }
        )
        OutlinedTextField(
            value = uiState.price,
            onValueChange = { viewModel.addPrice(it) },
            label = { Text("Price (Optional)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.offerRide() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Offer Ride")
        }
    }
}