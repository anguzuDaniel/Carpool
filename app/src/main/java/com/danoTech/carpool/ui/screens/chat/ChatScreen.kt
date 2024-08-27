package com.danoTech.carpool.ui.screens.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.ui.PageWithBackButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatScreen(
    receiverId: String,
    onBackClicked: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val chatMessages by viewModel.chatMessages.collectAsState(initial = listOf())
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getChatMessages(receiverID = receiverId)
        Log.d("ChatScreen", "Chat messages: $chatMessages")
    }

    PageWithBackButton(title = "Chat", onBackButtonClicked = onBackClicked) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                reverseLayout = true,
                verticalArrangement = Arrangement.Top
            ) {
                items(chatMessages) { message ->
                    if (message != null) {
                        ChatMessage(message.content, message.senderId == FirebaseAuth.getInstance().currentUser?.uid)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                ChatBar(
                    receiverId = receiverId,
                    uiState = uiState,
                    viewModel = viewModel,
                )
            }
        }
    }
}