package com.danoTech.carpool.ui.screens.request_ride

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestRideScreen(
    onBack: () -> Unit,
    onClose: () -> Unit,
    onNavItemClicked: (String) -> Unit,
    viewModel: RideRequestViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    openDrawerNavigation: () -> Unit
) {
    MapScreenWithSearch(
        city = "Uganda",
        country = "Kampala",
        onBack = onBack,
        onClose = onClose,
        viewModel = viewModel,
        onNavItemClicked = {
            onNavItemClicked(it)
        },
        modifier = modifier,
        openDrawerNavigation = openDrawerNavigation
    )
}
