package com.danoTech.carpool.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    private val _availableRides = MutableStateFlow<List<Ride>>(emptyList())
    val availableRides: StateFlow<List<Ride>> = _availableRides.asStateFlow()

    init {
        // Fetch available rides when the ViewModel is initialized
        viewModelScope.launch {
            val fetchedRides = fetchAvailableRides()
            _availableRides.value = fetchedRides
        }
    }

    private suspend fun fetchAvailableRides(): List<Ride> {
        return listOf(
            Ride("Ride from City A to City B"),
            Ride("Airport Transfer - City C"),
            Ride("Weekend Trip to City D")
        )
    }
}