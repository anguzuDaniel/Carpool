package com.danoTech.carpool.ui.screens.map

import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.ui.screens.cars.CarpoolScreen
import com.danoTech.carpool.ui.screens.components.LoadingPage
import com.danoTech.carpool.ui.screens.request_ride.RideRequestViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenWithModalBottomSheet(
    country: String,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel = hiltViewModel(),
    currentLocation: Location?,
    geocoder: Geocoder,
    onNavItemClicked: (String) -> Unit,
    onSearchPool: (String) -> Unit,
    isCarPool: Boolean
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val carpoolOptionText by remember { mutableStateOf("") }
    var isInputActive by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.collectAsState().value
    val rideRequestState = viewModel.rideRequestUiState.collectAsState().value

    LaunchedEffect(key1 = carpoolOptionText) {
        if (carpoolOptionText.isBlank() && sheetState.currentValue == SheetValue.Expanded) {
            scope.launch { sheetState.partialExpand() }
            isInputActive = false
        } else if (carpoolOptionText.isNotBlank() && sheetState.currentValue != SheetValue.Expanded) {
            scope.launch { sheetState.expand() }
            isInputActive = true
        }
    }

    if (uiState.isLoading) {
        LoadingPage(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currentLocation?.let { location ->
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses?.firstOrNull()
                val locationName = address?.featureName ?: ""

                Box {
                    MapDisplay(
                        viewModel = viewModel,
                        country = country,
                        currentLocation = location,
                        modifier = Modifier.fillMaxHeight(.55f)
                    )
                }

                CarpoolScreen(
                    modifier = Modifier.fillMaxHeight(.5f),
                    pickupLocation = rideRequestState.pickupLocation.ifEmpty { locationName },
                    estimatedTime = rideRequestState.estimatedTime,
                    numberOfSeats = rideRequestState.numberOfSeats,
                    price = rideRequestState.price,
                    destination = rideRequestState.destination,
                    onNavItemClick = {
                        onNavItemClicked(it)
                    },
                    onDestinationChanged = {
                        viewModel.onDestinationChanged(it.lowercase())
                    },
                    onSearchCarPool = {
                        onSearchPool(uiState.destination)
                    },
                    availableCars = rideRequestState.availableCars,
                    isInCarpool = isCarPool,
                    onCancelCarpool = {
                        showConfirmationDialog = true

                    }
                )
            }
        }
    }

    // Confirmation Dialog for Canceling Carpool
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Cancel Carpool") },
            text = { Text("Are you sure you want to cancel the carpool? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cancelCarpool() // Call the method to cancel the carpool
                        showConfirmationDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenWithBottomSheetScaffold(
    country: String,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel = hiltViewModel(),
    currentLocation: Location?,
    geocoder: Geocoder,
    onNavItemClicked: (String) -> Unit,
    onSearchPool: (String) -> Unit,
    isCarPool: Boolean
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val carpoolOptionText by remember { mutableStateOf("") }
    var isInputActive by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.collectAsState().value
    val rideRequestState = viewModel.rideRequestUiState.collectAsState().value

    LaunchedEffect(key1 = carpoolOptionText) {
        if (carpoolOptionText.isBlank() && sheetState.currentValue == SheetValue.Expanded) {
            scope.launch { sheetState.partialExpand() }
            isInputActive = false
        } else if (carpoolOptionText.isNotBlank() && sheetState.currentValue != SheetValue.Expanded) {
            scope.launch { sheetState.expand() }
            isInputActive = true
        }
    }

    if (uiState.isLoading) {
        LoadingPage(modifier = Modifier.fillMaxSize())
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currentLocation?.let { location ->
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses?.firstOrNull()
                val locationName = address?.featureName ?: ""

                Box {
                    MapDisplay(
                        viewModel = viewModel,
                        country = country,
                        currentLocation = location,
                        modifier = Modifier.fillMaxHeight(.55f)
                    )
                }

                CarpoolScreen(
                    modifier = Modifier.fillMaxHeight(.5f),
                    pickupLocation = rideRequestState.pickupLocation.ifEmpty { locationName },
                    estimatedTime = rideRequestState.estimatedTime,
                    numberOfSeats = rideRequestState.numberOfSeats,
                    price = rideRequestState.price,
                    destination = rideRequestState.destination,
                    onNavItemClick = {
                        onNavItemClicked(it)
                    },
                    onDestinationChanged = {
                        viewModel.onDestinationChanged(it.lowercase())
                    },
                    onSearchCarPool = {
                        onSearchPool(uiState.destination)
                    },
                    availableCars = rideRequestState.availableCars,
                    isInCarpool = isCarPool,
                    onCancelCarpool = {
                        showConfirmationDialog = true
                    }
                )
            }
        }
    }
}