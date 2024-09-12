package com.danoTech.carpool.model

import com.google.firebase.firestore.DocumentId

data class Passenger(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val seatNumber: Int? = null,
    val isConfirmed: Boolean = false
)