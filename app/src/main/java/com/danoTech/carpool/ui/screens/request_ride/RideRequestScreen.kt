package com.danoTech.carpool.ui.screens.request_ride

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danoTech.carpool.ui.screens.signin.SignupScreen
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun RideRequestScreen(
    rideRequestViewModel: RideRequestViewModel = hiltViewModel()
) {
    val rideRequests = rideRequestViewModel.rideRequests.collectAsState(initial = emptyList())

    Column {
        rideRequests.value.forEach { rideRequest ->
            Text(text = "Rider: ${rideRequest.riderName}")
            Text(text = "Pickup: ${rideRequest.pickupLocation}")
            Text(text = "Destination: ${rideRequest.destination}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RideRequestScreenPreview() {
    CarpoolTheme {
        RideRequestScreen()
    }
}