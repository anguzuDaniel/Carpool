package com.danoTech.carpool.ui

sealed class Routes(val route: String) {
    data object Intro : Routes("Intro")
    data object Login : Routes("Login")
    data object Signup : Routes("Signup")
    data object ForgotPassword : Routes("ForgotPassword")
    data object Profile : Routes("Profile")
    data object RequestRide : Routes("RequestRide")
    data object Home : Routes("Home")
    data object OfferRide : Routes("OfferRide")
    data object AvailableRides : Routes("Available Rides")
    data object Chat : Routes("ChatUiState")
    data object CarDisplay : Routes("CarDisplay")
    data object RegisterDriver : Routes("RegisterDriver")
}