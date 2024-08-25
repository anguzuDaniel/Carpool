package com.danoTech.carpool.ui.screens.chat

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.danoTech.carpool.model.Message

@Composable
fun MessageItem(message: Message) {
    // Implement message layout based on senderId
    // For example, you can use different styles for sent and received messages
    Text(text = message.content)
}
