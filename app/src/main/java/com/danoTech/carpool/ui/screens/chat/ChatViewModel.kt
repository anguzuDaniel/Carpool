package com.danoTech.carpool.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatViewModel @JvmOverloads constructor(
    private val storageService: StorageService,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<String>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()

    fun getChatMessages() {
        viewModelScope.launch {
            val chatMessages = storageService.getChatMessages().first()
            _chatMessages.value = chatMessages.map { it?.content ?: "" }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: "Anonymous"
            storageService.addChatMessage(userId, message)
        }
    }

}