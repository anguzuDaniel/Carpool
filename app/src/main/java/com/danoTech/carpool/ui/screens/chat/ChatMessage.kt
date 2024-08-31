package com.danoTech.carpool.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.model.Message
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun ChatMessage(
    text: String,
    isFromMe: Boolean,
    isSeen: Boolean = false
) {
    val textPadding = 12.dp
    val messagePaddingVertical = 3.dp
    val messagePaddingHorizontal = 12.dp
    val backgroundColor = if (isFromMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
    val textStyle = if (isFromMe) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium) else MaterialTheme.typography.bodyMedium

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = messagePaddingHorizontal, vertical = messagePaddingVertical),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = if (isFromMe) RoundedCornerShape(
                        textPadding,
                        0.dp,
                        textPadding,
                        textPadding
                ) else RoundedCornerShape(0.dp, textPadding, textPadding, textPadding)
        ),
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 5.dp),
                style = textStyle,
                color = Color.White,
                textAlign = if (isFromMe) TextAlign.End else TextAlign.Start,
            )

            Icon(
                imageVector = if (isSeen) Icons.Default.CheckCircle else Icons.Default.CheckCircleOutline,
                contentDescription = "Seen",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 10.dp)
                    .align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessagePreview() {
    val messages = mutableListOf(
        Message(
            senderId = "1",
            content = "Hey"
        ),
        Message(
            senderId = "1",
            content = "It's Daniel. How are you?",
        ),
        Message(
            senderId = "2",
            content = "Hi"
        ),
        Message(
            senderId = "1",
            content = "I'm fine, thanks"
        ),
        Message(
            senderId = "2",
            content = "I'm also fine"
        )
    )
    CarpoolTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            messages.forEach { ChatMessage(
                text = it.content,
                isFromMe = it.senderId == "1",
                isSeen = it.senderId == "2"
            ) }
        }
    }
}