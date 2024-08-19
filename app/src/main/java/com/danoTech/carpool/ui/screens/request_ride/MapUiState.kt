package com.danoTech.carpool.ui.screens.request_ride

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

data class MapUiState(
    var properties: MapProperties = MapProperties(
        mapStyleOptions = MapStyleOptions(MapStyle.json),
    ),
    var isFalloutMap: Boolean = false,
    var query: String = "",
    val isLoading: Boolean = false,
    val businesses: List<RideRequestUiState> = emptyList(),
    val destination: String = ""
)