package com.danoTech.carpool.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatBar(
    receiverId: String,
    uiState: ChatUiState,
    viewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = uiState.message,
            onValueChange = { viewModel.onMessageChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ),
            placeholder = { Text("Type your message...") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { viewModel.onSendClicked(receiverId) }),
            maxLines = 2, // Allow for multi-line messages
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                disabledIndicatorColor = MaterialTheme.colorScheme.surface,
            )
        )

        IconButton(
            onClick = { viewModel.onSendClicked(receiverId) },
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
                .weight(.2f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Send"
            )
        }
    }
}