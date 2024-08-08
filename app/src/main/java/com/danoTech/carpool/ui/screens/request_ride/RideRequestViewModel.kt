package com.danoTech.carpool.ui.screens.request_ride

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.CarPoolViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideRequestViewModel @Inject constructor(
    private val accountService: AccountService
) : CarPoolViewModel() {
    private val _rideRequests = MutableStateFlow<List<RideRequest>>(emptyList())
    val rideRequests: StateFlow<List<RideRequest>> = _rideRequests

    init {
        fetchRideRequests()
    }

    private fun fetchRideRequests() {
        viewModelScope.launch {
            // Replace this with actual logic to fetch ride requests from the database
            val fetchedRideRequests = listOf(
                RideRequest("John Doe", "Kampala", "Entebbe"),
                RideRequest("Jane Smith", "Mbarara", "Masaka")
            )
            _rideRequests.value = fetchedRideRequests
        }
    }
}