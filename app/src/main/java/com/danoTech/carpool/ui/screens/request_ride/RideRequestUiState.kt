package com.danoTech.carpool.ui.screens.request_ride

data class RideRequestUiState(
    val riderName: String  = "",
    val pickupLocation: String = "",
    val searchMessage: String = "",
    val destination: String = "",
    val estimatedTime: String = "",
    val price: String = "",
    val numberOfSeats: Int = 0,
    var isSearchingForRide: Boolean = false,
    var isErrorSearch: Boolean = false
)
