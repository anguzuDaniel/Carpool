package com.danoTech.carpool.ui.screens.offer_ride

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class RideOfferViewModel @Inject constructor(
    private val storageService: StorageService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(OfferedRideUiState())
    val uiState = _uiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun offerRide() {
        val car = Car(
            id = UUID.randomUUID().toString(),
            name = uiState.value.name,
            driverName = uiState.value.driverName,
            make = uiState.value.make,
            model = uiState.value.model,
            year = uiState.value.year.year,
            pickupLocation = uiState.value.pickupLocation,
            destination = uiState.value.destination,
            seatsAvailable = uiState.value.seatsAvailable,
            departureTime = uiState.value.departureTime.toString(),
            price = uiState.value.price
        )

        _uiState.value = _uiState.value.copy(
            loading = true
        )

        viewModelScope.launch {
            try {
                storageService.save(car)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    hasError = true,
                    message = "Something went wring, ${e.message}"
                )
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                loading = false,
                hasMessage = true,
                message = "Ride Offer saved."
            )
        }
    }

    fun updateSelectedDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(
            departureTime = date
        )
    }

    fun addDestination(location: String) {
        _uiState.value = _uiState.value.copy(
            destination = location
        )
    }

    fun addPickUpLocation(location: String) {
        _uiState.value = _uiState.value.copy(
            pickupLocation = location
        )
    }

    fun addPrice(price: Int) {
        _uiState.value = _uiState.value.copy(
            price = price.toString()
        )
    }

    fun addNumberOfSeats(i: Int) {

    }
}