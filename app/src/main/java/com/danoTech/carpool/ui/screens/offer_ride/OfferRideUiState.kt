package com.danoTech.carpool.ui.screens.offer_ride

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class OfferedRideUiState(
    val id: String = "",
    val name: String = "",
    val driverName: String = "",
    val model: String = "",
    val year: String = "2023",
    val date: String = "",
    val pickupLocation: String = "",
    val destination: String = "",
    var seatsAvailable: Int = 0,
    var departureTime: String = "",
    val price: String = "",
    val make: String = "",
    val message: String = "",
    val hasError: Boolean = false,
    val hasMessage: Boolean = false,
    val loading: Boolean = false
)