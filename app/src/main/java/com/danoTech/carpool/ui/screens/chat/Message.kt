package com.danoTech.carpool.ui.screens.chat

data class Message(
    val senderId: String,
    val content: String,
    val timestamp: Long
)
