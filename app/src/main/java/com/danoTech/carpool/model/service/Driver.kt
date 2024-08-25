package com.danoTech.carpool.model.service

import com.google.firebase.firestore.DocumentId

data class Driver(
    @DocumentId
    val id: String = "",
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)