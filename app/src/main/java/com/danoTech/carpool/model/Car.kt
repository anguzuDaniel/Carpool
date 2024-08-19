package com.danoTech.carpool.model

import com.google.firebase.firestore.DocumentId

data class Car(
    @DocumentId
    val id: String,
    val name: String,
    val driverName: String,
    val make: String,
    val model: String,
    val year: Int,
    val pickupLocation: String,
    val destination: String,
    val seatsAvailable: Int,
    val departureTime: String,
    val price: String = ""
)