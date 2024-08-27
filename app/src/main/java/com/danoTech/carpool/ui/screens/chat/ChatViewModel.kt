package com.danoTech.carpool.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.Message
import com.danoTech.carpool.model.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val storageService: StorageService,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<Message?>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()

    fun getChatMessages(receiverID: String) {
        viewModelScope.launch {
            val chatFlow = storageService.getChatMessages(receiverID, auth.currentUser?.uid ?: "")
            chatFlow.collect { messages ->
                _chatMessages.value = messages.filterNotNull()
            }
        }
    }

    private fun sendMessage(receiverId: String, message: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: "Anonymous"
            val conversationId = UUID.randomUUID().toString()

            val newMessage = Message(
                senderId = userId,
                receiverId = receiverId,
                content = message,
                conversationId = conversationId
            )

            storageService.addChatMessage(newMessage)
        }
    }

    fun onMessageChanged(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }

    fun onSendClicked(receiverId: String) {
        sendMessage(receiverId, _uiState.value.message)
        _uiState.value = _uiState.value.copy(message = "")
    }
}