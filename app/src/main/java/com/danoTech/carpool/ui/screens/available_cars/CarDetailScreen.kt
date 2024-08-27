package com.danoTech.carpool.ui.screens.available_cars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.R
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.ui.screens.components.CarPoolButton

@Composable
fun CarDetailsScreen(
    car: Car,
    modifier: Modifier = Modifier,
    onCarClicked: () -> Unit = {},
    onBookClicked: () -> Unit = {},
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)

        ) {
            CarPoolButton(
                onClicked = onBookClicked,
                name = R.string.book_now
            )

            CarPoolButton(
                onClicked = {
                    onChatClick(car.id)
                },
                name = R.string.chat_with_driver
            )
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