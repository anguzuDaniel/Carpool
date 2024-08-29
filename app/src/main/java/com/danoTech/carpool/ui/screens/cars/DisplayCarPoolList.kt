package com.danoTech.carpool.ui.screens.cars

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.R
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.ui.screens.components.LoadingPage
import com.danoTech.carpool.ui.screens.request_ride.RideRequestViewModel
import java.util.Locale

@Composable
fun DisplayCarPoolList(
    destination: String,
    onCarClick: (String) -> Unit,
    modifier: Modifier = Modifier,
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
                    CarCardDisplay(
                        onClick = onCarClick,
                        car = car
                    )
                }
            }
        }
    }
}

@Composable
fun CarCardDisplay(
    onClick: (String) -> Unit = {},
    car: Car
) {
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clickable { onClick(car.id) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.adobe_stock_1),
                contentDescription = "Default car",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = car.driverName,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )

                    Text(
                        text = "${car.price} Ugx",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                VerticalDivider(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationSearching,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(15.dp)
                        )

                        Text(
                            text = car.pickupLocation.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            },
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(15.dp)
                        )

                        Text(
                            text = car.destination.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            },
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}