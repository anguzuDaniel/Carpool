package com.danoTech.carpool.ui.screens.chat

import com.danoTech.carpool.model.Message


/**
 * UI state for the chat screen.
 *
 * @param messages A list of messages to display in the chat.
 * @param isLoading Whether the chat is currently loading.
 * @param isCreateAccountError Whether there was an error creating an account.
 * @param isCreateAccountSuccess Whether the account creation was successful.
 * @param isCreateAccountInProgress Whether the account creation is in progress.
 * @param message A message to display to the user.
 */
data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val isCreateAccountError: Boolean = false,
    val isCreateAccountSuccess: Boolean = false,
    val isCreateAccountInProgress: Boolean = false,
    val message: String = ""
)