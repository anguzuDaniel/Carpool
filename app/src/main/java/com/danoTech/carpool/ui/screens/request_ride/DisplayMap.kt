package com.danoTech.carpool.ui.screens.request_ride

import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MapDisplay(
    country: String,
    currentLocation: Location,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    val gc = Geocoder(context, Locale.getDefault())

    // Location of Kampala, Uganda (default)
    val defaultLocation = LatLng(currentLocation.latitude, currentLocation.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }

    var searchResults by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var showCarAvailability by remember { mutableStateOf(false) }
    var areCarsAvailable by remember { mutableStateOf(false) }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
    ) {
        // Default marker
        Marker(
            state = MarkerState(position = defaultLocation), title = country,
            snippet = "Marker in $country"
        )

        // Markers for search results
        searchResults.forEach { location ->
            Marker(
                state = MarkerState(position = location),
                onClick = {
                    selectedLocation = location
                    showCarAvailability = true
                    // Check car availability (e.g., viewModel.checkCarAvailability(location))
                    true
                }
            )
        }

        // Marker for selected location
        selectedLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Selected Location"
            )
        }
    }

    // Display car availability (adapt to your UI)
    if (showCarAvailability) {
        AlertDialog(
            onDismissRequest = { showCarAvailability = false },
            title = { Text("Car Availability") },
            text = { Text("Cars available at this location: ${if (areCarsAvailable) "Yes" else "No"}") },
            confirmButton = {
                if (areCarsAvailable) {
                    Button(onClick = {
                        // Handle connecting the user to an available car
                        // (e.g., viewModel.connectToCar(selectedLocation))
                        showCarAvailability= false
                    }) {
                        Text("Request Ride")
                    }
                }
            },
            dismissButton = {
                Button(onClick = { showCarAvailability = false }) {
                    Text("Dismiss")
                }
            }
        )
    }
}