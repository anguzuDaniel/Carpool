package com.danoTech.carpool.ui.screens.request_ride

import com.danoTech.carpool.model.Car

data class RideRequestUiState(
    val riderName: String  = "",
    val pickupLocation: String = "",
    val searchMessage: String = "",
    val destination: String = "",
    val estimatedTime: String = "",
    val availableCars: List<Car> = emptyList(),
    val price: String = "",
    val numberOfSeats: Int = 0,
    var isSearchingForRide: Boolean = false,
    var isErrorSearch: Boolean = false,
    val isCarpoolStarted: Boolean = false,
    val isRideRequested: Boolean = false,
    val carpoolMessage: String = "",
    val rideRequestMessage: String = "",
    val currentCarpool: Car? = null,
    val passengers: List<String> = emptyList()
)
