package com.danoTech.carpool.ui.screens.cars

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.R
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.ui.screens.components.LoadingPage
import com.danoTech.carpool.ui.screens.request_ride.RideRequestViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.util.Locale

@Composable
fun DisplayCarPoolList(
    destination: String,
    onCarClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onChatClick: (String) -> Unit = {},
    onRequestPoolClick: (String) -> Unit = {},
    viewModel: RideRequestViewModel = hiltViewModel()
) {
    val uiState = viewModel.rideRequestUiState.collectAsState().value

    LaunchedEffect(viewModel) {
        viewModel.searchForCarpool(destination)
        Log.d("AvailableCars", uiState.availableCars.toString())
    }

    if (uiState.isSearchingForRide) {
        LoadingPage(
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier.padding(16.dp)
        ) {
            if (uiState.availableCars.isEmpty()) {
                item {
                    Text(
                        text = uiState.searchMessage,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                items(uiState.availableCars) { car ->
                    CarCardItemDisplay(
                        onClick = onCarClick,
                        car = car,
                        onChatClick = onChatClick,
                        onRequestPoolClick = {
                            viewModel.joinCarpool(it, Firebase.auth.currentUser!!.uid)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarCardItemDisplay(
    onClick: (String) -> Unit = {},
    car: Car,
    onChatClick: (String) -> Unit = {},
    onRequestPoolClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small)
                        .border(1.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.adobe_stock_1),
                        contentDescription = "Default car",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = car.driverName,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${car.price} Ugx",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationSearching,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = car.pickupLocation.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                            },
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = car.destination.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                            },
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onChatClick(car.id) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chat with Driver",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { onRequestPoolClick(car.id) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CarRental,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Request for Pool",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CarCardDisplayPreview() {
    CarCardItemDisplay(
        car = Car(
            id = "1",
            userId = "",
            driverName = "Anguzu Daniel",
            name = "Car 1",
            destination = "Destination",
            price = "10000",
            seatsAvailable = 1,
            pickupLocation = "Pickup Location 1"
        )
    )
}