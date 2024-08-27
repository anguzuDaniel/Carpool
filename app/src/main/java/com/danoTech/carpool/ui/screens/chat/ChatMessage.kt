package com.danoTech.carpool.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ChatMessage(text: String, isFromMe: Boolean) {
    val textPadding = 12.dp
    val messagePaddingVertical = 3.dp
    val messagePaddingHorizontal = 12.dp
    val backgroundColor = if (isFromMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    val messageColor = if (isFromMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val textStyle = if (isFromMe) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium) else MaterialTheme.typography.bodyMedium

    Box(
        modifier = Modifier
            .padding(horizontal = messagePaddingHorizontal, vertical = messagePaddingVertical)
            .background(
                color = backgroundColor,
                shape = if (isFromMe) RoundedCornerShape(
                    textPadding,
                    0.dp,
                    textPadding,
                    textPadding
                ) else RoundedCornerShape(0.dp, textPadding, textPadding, textPadding)
            ),
        contentAlignment = if (isFromMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            style = textStyle,
            color = messageColor,
            textAlign = if (isFromMe) TextAlign.End else TextAlign.Start,
        )
    }
}