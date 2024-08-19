package com.danoTech.carpool.ui.screens.offer_ride

import java.time.LocalDate

data class OfferedRideUiState(
    val id: String = "",
    val name: String = "",
    val driverName: String = "",
    val model: String = "",
    val year: LocalDate = LocalDate.now(),
    val date: String = "",
    val pickupLocation: String = "",
    val destination: String = "",
    var seatsAvailable: Int = 0,
    val departureTime: LocalDate = LocalDate.now(),
    val price: String = "",
    val make: String = ""
)