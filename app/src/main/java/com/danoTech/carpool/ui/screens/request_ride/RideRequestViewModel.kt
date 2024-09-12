package com.danoTech.carpool.ui.screens.request_ride

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.service.StorageService
import com.danoTech.carpool.ui.screens.map.MapEvent
import com.danoTech.carpool.ui.screens.map.MapStyle
import com.danoTech.carpool.ui.screens.map.MapUiState
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideRequestViewModel
@Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    private val _rideUiState = MutableStateFlow(RideRequestUiState())
    val rideRequestUiState = _rideUiState.asStateFlow()

    init {
        getCurrentLocation()
    }

    fun onClose() {
        _uiState.value = _uiState.value.copy(query = "")
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.toggleFalloutMap -> {
                _uiState.value = _uiState.value.copy(
                    properties = _uiState.value.properties.copy(
                        mapStyleOptions = if (_uiState.value.isFalloutMap) {
                            null
                        } else {
                            MapStyleOptions(MapStyle.json)
                        }
                    ),
                    isFalloutMap = !_uiState.value.isFalloutMap
                )
            }

            else -> TODO()
        }
    }

    fun onSearchInput() {
        viewModelScope.launch {

        }
    }

    fun onCarPoolMessageChanged(newValue: String) {
        _rideUiState.value = _rideUiState.value.copy(carpoolMessage = newValue)
    }

    fun onQueryChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(query = newValue)
    }

    fun onSearch(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    fun searchBusinesses() {
    }

    private fun fetchRideRequests() {
        viewModelScope.launch {

        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

    }

    fun searchForCarpool(destination: String) {
        _rideUiState.value = _rideUiState.value.copy(
            isSearchingForRide = true,
            searchMessage = "Searching for available ride..."
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foundCars = storageService.getAvailableRide(destination).first()
                _rideUiState.value = _rideUiState.value.copy(
                    isSearchingForRide = false,
                    searchMessage = if (foundCars.isEmpty()) "No cars found for the searched destination" else "",
                    availableCars = foundCars
                )
            } catch (exception: Exception) {
                _rideUiState.value = _rideUiState.value.copy(
                    isSearchingForRide = false,
                    isErrorSearch = true,
                    searchMessage = "Sorry, something went wrong. ${exception.message}"
                )
                Log.d("Found Cars", exception.message.toString())
            }
        }
    }

    fun onDestinationChanged(destination: String) {
        _uiState.value = _uiState.value.copy(
            destination = destination
        )
    }

    fun isDialogOpen(isClosed: Boolean) {
        _rideUiState.value = _rideUiState.value.copy(
            isSearchingForRide = isClosed
        )
    }

    // Request a ride for an existing carpool
    fun requestRide(carpoolId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                storageService.requestRide(carpoolId, accountService.currentUser)
                _rideUiState.value = _rideUiState.value.copy(
                    isRideRequested = true,
                    rideRequestMessage = "Ride requested successfully!"
                )
            } catch (exception: Exception) {
                _rideUiState.value = _rideUiState.value.copy(
                    isRideRequested = false,
                    rideRequestMessage = "Failed to request ride. ${exception.message}",
                    isErrorSearch = true
                )
                Log.e("Request Ride", "Error: ${exception.message}")
            }
        }
    }

    fun isUserInActiveCarpool(userId: String): Boolean {
        var isInActiveCarpool = false

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val cars = storageService.getCars()

                val activeCarpools = cars.filter {
                    it.active
                }

                val userCarpool = activeCarpools.find { it.passengers.contains(userId) }

                if (userCarpool != null) {
                    isInActiveCarpool = true

                    _rideUiState.value = _rideUiState.value.copy(
                        currentCarpool = userCarpool,
                        destination = userCarpool.destination,
                        pickupLocation = userCarpool.pickupLocation,
                        price = userCarpool.price,
                        isCarpoolStarted = true
                    )
                }
            } catch (e: Exception) {
                _rideUiState.value = _rideUiState.value.copy(
                    carpoolMessage = "Error checking carpool status: ${e.message}",
                    isRideRequested = false
                )
            } finally {
                if (isInActiveCarpool) {
                    _rideUiState.value = _rideUiState.value.copy(
                        carpoolMessage = "You are currently in an active carpool.",
                        isRideRequested = false
                    )
                } else {
                    // User is not in an active carpool
                    _rideUiState.value = _rideUiState.value.copy(
                        carpoolMessage = "You are not currently in an active carpool.",
                        isRideRequested = false
                    )
                }
            }
        }

        return isInActiveCarpool
    }

    fun joinCarpool(carpoolId: String, userId: String): Boolean {
        var rideStarted = false

        viewModelScope.launch(Dispatchers.IO) {
            val carpool = storageService.getCarpool(carpoolId).first()
            carpool?.let {
                if (it.seatsAvailable > 0 && !it.passengers.contains(userId)) {
                    val updatedPassengers = it.passengers + userId
                    val updatedCarpool = it.copy(
                        passengers = updatedPassengers,
                        seatsAvailable = it.seatsAvailable - 1,
                        active = true
                    )

                    storageService.updateCarpool(updatedCarpool)

                    _rideUiState.value = _rideUiState.value.copy(
                        currentCarpool = updatedCarpool,
                        isRideRequested = true,
                        carpoolMessage = "Joined the carpool successfully!"
                    )

                    rideStarted = true
                } else {
                    if (it.passengers.contains(userId)) {
                        _rideUiState.value = _rideUiState.value.copy(
                            carpoolMessage = "You are already in the carpool.",
                            isRideRequested = false
                        )
                    } else {
                        _rideUiState.value = _rideUiState.value.copy(
                            carpoolMessage = "No seats available in this carpool.",
                            isRideRequested = false,
                        )
                    }

                    rideStarted = false
                }
            }
        }

        return rideStarted
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}