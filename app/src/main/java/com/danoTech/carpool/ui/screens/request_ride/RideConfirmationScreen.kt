package com.danoTech.carpool.ui.screens.request_ride

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun RideConfirmationScreen(
    pickupLocation: String,
    numberOfSeats: Int,
    estimatedTime: String,
    price: String,
    onConfirmRide: () -> Unit,
    destination: String,
    onDestinationChanged: (String) -> Unit = {},
    onSearchCarPool: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Card(
            onClick = { /*TODO*/ },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        ) {
            Text(
                text = "Where would you like to go?",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight(.7f)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationSearching,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    VerticalDivider(
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onBackground.copy(.7f),
                        modifier = Modifier.weight(1f)
                    )
                }

                Column {
                    Text(
                        text = pickupLocation,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp, top = 10.dp),
                        color = MaterialTheme.colorScheme.onBackground.copy(.7f)
                    )

                    HorizontalDivider()

                    OutlinedTextField(
                        value = destination,
                        onValueChange = {
                            onDestinationChanged(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(
                            text = "Enter Destination",
                            style = MaterialTheme.typography.bodyMedium
                        ) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Button(
                        onClick = {
                            onSearchCarPool(destination)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = destination.isNotEmpty()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(end = 3.dp)
                                    .size(16.dp)
                            )
                            Text(
                                text = "Search for a Ride",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RideConfirmScreen() {
    CarpoolTheme {
        RideConfirmationScreen(
            // Populate data based on your app logic
            pickupLocation = "Kansanga", // Replace with actual location
            numberOfSeats = 1, // Replace with user selection or default
            estimatedTime = "Calculating...",
            price = "$10.00", // Replace with actual price
            onConfirmRide = {},
            destination = ""
        )
    }
}