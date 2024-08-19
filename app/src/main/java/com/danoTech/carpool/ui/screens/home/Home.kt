package com.danoTech.carpool.ui.screens.home

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    onRideRequestClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onOfferRideClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val availableRides = viewModel.availableRides.collectAsState().value

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 60.dp)
        ) {
            Row(

                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CTACard(
                    title = "Find Ride",
                    text = "Find a carpool",
                    modifier = Modifier.weight(1f)
                ) {
                    onRideRequestClick()
                }

                CTACard(
                    title = "Offer Ride",
                    text = "Find a carpool",
                    modifier = Modifier.weight(1f)
                ) {
                    onOfferRideClick()
                }
            }


//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (availableRides.isEmpty()) {
//                Text("No available rides at the moment.")
//            } else {
//                LazyColumn {
//                    items(availableRides) { ride ->
//                        RideItem(
//                            ride = ride,
//                            onClick = {}
//                        )
//                    }
//                }
//            }
        }
}

data class Ride(val details: String)

@Composable
fun RideItem(ride: Ride, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp) // Add rounded corners
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(ride.details, style = MaterialTheme.typography.titleLarge)
                // Add more ride details here as needed
                // (e.g., origin, destination, time, price)
                Text("Origin: City A", style = MaterialTheme.typography.bodyMedium)
                Text("Destination: City B", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = { /* Handle action */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Book Ride")
            }
        }
    }
}