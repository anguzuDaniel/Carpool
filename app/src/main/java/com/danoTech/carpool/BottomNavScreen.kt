package com.danoTech.carpool

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.danoTech.carpool.ui.Routes

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    data object Profile : BottomNavScreen(Routes.Profile.route, "Profile", Icons.Filled.Person)
    data object RideRequest : BottomNavScreen(Routes.RequestRide.route, "Find ride", Icons.Filled.CarRental)
    data object OfferRide : BottomNavScreen(Routes.OfferRide.route, "Offer ride", Icons.Filled.LocalTaxi)
}