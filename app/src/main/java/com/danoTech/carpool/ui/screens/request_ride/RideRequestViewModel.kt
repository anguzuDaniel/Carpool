package com.danoTech.carpool.ui.screens.request_ride

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.model.Car
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideRequestViewModel @Inject constructor(
    private val accountService: AccountService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()
    private val _availableCars = MutableStateFlow<List<Car>>(emptyList())
    val availableCars: StateFlow<List<Car>> = _availableCars.asStateFlow()

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

    fun searchForCarpool(destination: String) {
        viewModelScope.launch {
            // Perform the search for available cars based on the destination
            val foundCars = fetchAvailableCars(destination) // Replace with your actual search logic
            _availableCars.value = foundCars
        }
    }

    // Placeholder for your actual car fetching logic
    private suspend fun fetchAvailableCars(destination: String): List<Car> {
        // ... your implementation to fetch cars based on destination
        return emptyList() // Replace with the actual list of found cars
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}