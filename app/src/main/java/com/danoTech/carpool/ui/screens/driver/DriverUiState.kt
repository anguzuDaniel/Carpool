package com.danoTech.carpool.ui.screens.driver

data class DriverUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val isLoading: Boolean = false
)