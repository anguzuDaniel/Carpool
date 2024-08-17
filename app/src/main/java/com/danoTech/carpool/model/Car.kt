package com.danoTech.carpool.model

import com.google.firebase.firestore.DocumentId

data class Car(
    @DocumentId
    val id: String,
    val name: String,
    val make: String,
    val model: String,
    val year: Int
)