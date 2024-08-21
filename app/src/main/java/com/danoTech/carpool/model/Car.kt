package com.danoTech.carpool.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId

data class Car(
    @DocumentId
    val id: String = "",
    val userId: String = FirebaseAuth.getInstance().currentUser!!.email.toString(),
    val name: String = "",
    val driverName: String = "",
    val make: String = "",
    val model: String = "",
    val year: Int = 2023,
    val pickupLocation: String = "",
    val destination: String = "",
    val seatsAvailable: Int = 0,
    val departureTime: String = "",
    val price: String = ""
)