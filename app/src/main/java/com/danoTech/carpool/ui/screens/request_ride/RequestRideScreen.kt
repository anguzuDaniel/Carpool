package com.danoTech.carpool.ui.screens.request_ride

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestRideScreen(
    onBack: () -> Unit,
    onClose: () -> Unit,
    viewModel: RideRequestViewModel = hiltViewModel()
) {
    MapScreenWithSearch(
        city = "Uganda",
        country = "Kampala",
        onBack = onBack,
        onClose = onClose,
        viewModel = viewModel
    )
}
