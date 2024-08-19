package com.danoTech.carpool.ui.screens.request_ride

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.service.StorageService
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class RideRequestViewModel
@Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()
    private val _availableCars = MutableStateFlow<List<Car>>(emptyList())
    val availableCars: StateFlow<List<Car>> = _availableCars.asStateFlow()

    private val cancellationTokenSource = CancellationTokenSource()
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
        }
    }

    fun onSearchInput() {
        viewModelScope.launch {

        }
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

    fun searchForCarpool() {
        _rideUiState.value = _rideUiState.value.copy(
            isSearchingForRide = true,
            searchMessage = "Searching for available ride..."
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foundCars = storageService.getAvailableRide(uiState.value.destination).first().first()
                _availableCars.value = foundCars ?: emptyList()
            } catch (exception: Exception) {
                _rideUiState.value = _rideUiState.value.copy(
                    isErrorSearch = true,
                    searchMessage = "Sorry, we could not find any available rides."
                )
            }
        }
    }

    fun onDestinationChanged(destination: String) {
        _uiState.value = _uiState.value.copy(
            destination = destination
        )
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}