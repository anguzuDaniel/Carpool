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
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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

    fun cancelCarpool() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = FirebaseAuth.getInstance().currentUser?.email

            try {
                val carpool = storageService.getCars().firstOrNull { it.first().passengers.contains(currentUser) }?.firstOrNull()

                if (carpool != null) {
                    val updatedPassengers = carpool.passengers - currentUser
                    val updatedCarpool = carpool.copy(
                        passengers = updatedPassengers,
                        seatsAvailable = carpool.seatsAvailable + 1
                    )

                    // Update the carpool in storage
                    storageService.updateCarpool(updatedCarpool)

                    _rideUiState.value = _rideUiState.value.copy(
                        currentCarpool = null,
                        isCarpoolStarted = false,
                        isRideRequested = false,
                        carpoolMessage = "Carpool canceled successfully."
                    )
                } else {
                    _rideUiState.value = _rideUiState.value.copy(
                        carpoolMessage = "No active carpool to cancel."
                    )
                }
            } catch (exception: Exception) {
                _rideUiState.value = _rideUiState.value.copy(
                    carpoolMessage = "Failed to cancel carpool. ${exception.message}",
                    isErrorSearch = true
                )
                Log.e("Cancel Carpool", "Error: ${exception.message}")
            }
        }
    }

    fun checkUserInActiveCarpool(userId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                // Collect the Flow from getCars() method
                storageService.getCars().collect { cars ->
                    val activeCarpools = cars.filter { it.active }

                    Log.d("checkUserInActiveCarpool", "activeCarpools: $activeCarpools")

                    val userCarpool = activeCarpools.find { it.passengers.contains(userId) }

                    Log.d("checkUserInActiveCarpool", "userCarpool: $userCarpool")

                    if (userCarpool != null) {
                        _rideUiState.value = _rideUiState.value.copy(
                            currentCarpool = userCarpool,
                            destination = userCarpool.destination,
                            pickupLocation = userCarpool.pickupLocation,
                            price = userCarpool.price,
                            isCarpoolStarted = true,
                            carpoolMessage = "You are currently in an active carpool.",
                            isRideRequested = true
                        )
                    } else {
                        // User is not in an active carpool
                        _rideUiState.value = _rideUiState.value.copy(
                            carpoolMessage = "You are not currently in an active carpool.",
                            isRideRequested = false
                        )
                    }
                }
            } catch (e: Exception) {
                _rideUiState.value = _rideUiState.value.copy(
                    carpoolMessage = "Error checking carpool status: ${e.message}",
                    isRideRequested = false
                )
            }
        }
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