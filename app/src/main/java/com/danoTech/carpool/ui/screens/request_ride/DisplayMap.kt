package com.danoTech.carpool.ui.screens.request_ride

import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    city: String,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value
    val gc = Geocoder(context, Locale.getDefault())

    // location of kampala uganda
    val lat = 0.347596
    val lon = 32.582520

    val uganda = LatLng(lat, lon)

    // using the location of kampala uganda as the default location
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uganda, 10f)
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = uganda),
                title = country,
                snippet = "Marker in $country"
            )
        }
    }
}