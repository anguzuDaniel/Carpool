package com.danoTech.carpool.ui.screens.driver

data class RegistrationUiState(
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val carDetails: CarDetails? = null,
    val errorMessage: String? = null
)

data class CarDetails(
    val make: String? = null,
    val model: String? = null,
    val year: Int? = null,
    val licensePlate: String? = null,
    val insurance: String? = null,
    val driverLicense: String? = null
)

