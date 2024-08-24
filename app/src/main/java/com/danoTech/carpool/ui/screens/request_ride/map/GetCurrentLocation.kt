<<<<<<<< HEAD:app/src/main/java/com/danoTech/carpool/ui/screens/map/GetCurrentLocation.kt
package com.danoTech.carpool.ui.screens.map
========
package com.danoTech.carpool.ui.screens.request_ride.map
>>>>>>>> origin/master:app/src/main/java/com/danoTech/carpool/ui/screens/request_ride/map/GetCurrentLocation.kt

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationAvailable: (Location?) -> Unit
) {
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener { location ->
        onLocationAvailable(location)
    }
}