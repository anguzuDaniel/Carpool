package com.danoTech.carpool.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId

data class Profile(
    @DocumentId val id: String = "",
    val userId: String = FirebaseAuth.getInstance().currentUser!!.email.toString(),
    // to be changed to receive url links
    val profileImageUrl: String = "",
    val profileName: String = "",
    val firstName: String = "",
    val lastName: String = ""
)