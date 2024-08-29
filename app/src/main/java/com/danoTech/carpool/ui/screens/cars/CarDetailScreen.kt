package com.danoTech.carpool.ui.screens.cars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.R
import com.danoTech.carpool.model.Car

@Composable
fun CarDetailsScreen(
    car: Car,
    modifier: Modifier = Modifier,
    onCarClicked: () -> Unit = {},
    onChatClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onCarClicked)
    ) {
        Text(
            text = car.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Row   {
                IconButton(onClick = {
                    onChatClick(car.id)
                }) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = stringResource(R.string.chat_with_driver))
                }
                Text(
                    text = "Call",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Row {
                IconButton(onClick = {
                    onChatClick(car.id)
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Chat, contentDescription = stringResource(R.string.chat_with_driver))
                }
                Text(
                    text = "Call",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "Destination:",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = car.destination,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Price:",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = car.price,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Seats Available:",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = car.seatsAvailable.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "Pickup Location:",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = car.pickupLocation,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}