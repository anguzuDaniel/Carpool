package com.danoTech.carpool.ui.screens.map

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.ui.screens.request_ride.RideRequestViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MapDisplay(
    country: String,
    currentLocation: Location,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState = viewModel.rideRequestUiState.collectAsState().value
    val gc = Geocoder(context, Locale.getDefault())

    // Location of Kampala, Uganda (default)
    val defaultLocation = LatLng(currentLocation.latitude, currentLocation.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }

    val searchResults by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var showCarAvailability by remember { mutableStateOf(false) }
    val areCarsAvailable by remember { mutableStateOf(false) }

    // State for start and end locations
    var startLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var endLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }

    LaunchedEffect(uiState.currentCarpool) {
        // Update start and end markers based on carpool data
        uiState.currentCarpool?.let { carpool ->
            startLocation = getLatLngFromLocationName(context, carpool.pickupLocation) ?: LatLng(0.0, 0.0)
            endLocation = getLatLngFromLocationName(context, carpool.destination) ?: LatLng(0.0, 0.0)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapLoaded = {
            // Zoom to fit both start and end locations after the map is fully loaded
            startLocation.let { start ->
                endLocation.let { end ->
                    val bounds = LatLngBounds.Builder()
                        .include(start)
                        .include(end)
                        .build()

                    // Check that the CameraUpdateFactory is initialized only after the map is ready
                    cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 100))
                }
            }
        }
    ) {
        // Start location marker
        Marker(
            state = MarkerState(position = startLocation),
            title = "Pickup Location"
        )

        // End location marker
        Marker(
            state = MarkerState(position = endLocation),
            title = "Destination"
        )

        if (startLocation != LatLng(0.0, 0.0) && endLocation != LatLng(0.0, 0.0)) {
            Polyline(
                points = listOf(startLocation, endLocation),
                clickable = true,
                color = Color.Red,
                width = 5f
            )
        }

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

suspend fun getLatLngFromLocationName(context: Context, locationName: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context)
            val addressList = geocoder.getFromLocationName(locationName, 1)
            if (addressList?.isNotEmpty() == true) {
                val address = addressList[0]
                LatLng(address.latitude, address.longitude)
            } else {
                null
            }
        } catch (e: IOException) {
            Log.e("GeocoderError", "Geocoding failed: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e("GeocoderError", "An error occurred: ${e.message}")
            null
        }
    }
}