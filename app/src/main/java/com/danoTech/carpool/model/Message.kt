package com.danoTech.carpool.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.*

data class Message(
    @DocumentId @PropertyName("id") val id: String = UUID.randomUUID().toString(),
    @PropertyName("senderId") val senderId: String = "",
    @PropertyName("conversationId") val conversationId: String = "",
    @PropertyName("receiverId") val receiverId: String = "",
    @PropertyName("content") val content: String = "",
    @PropertyName("timestamp") val timestamp: Date = Date()
)
