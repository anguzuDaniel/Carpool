package com.danoTech.carpool.ui.screens.home

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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
    viewModel: HomeViewModel = hiltViewModel()
) {
    val availableRides = viewModel.availableRides.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Carpool App") }) },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimary
                        ) },
                    selected = false, // You might need to manage the selected state
                    onClick = onRideRequestClick
                )
                BottomNavigationItem(
                    icon = { Icon(
                        Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.onPrimary
                        ) },
                    selected = false,
                    onClick= onProfileClick
                )
                BottomNavigationItem(
                    icon = { Icon(
                        Icons.Filled.Settings,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Settings")
                           },
                    selected = false,
                    onClick = onSettingsClick
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRideRequestClick() },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text ="Request a Ride",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Find a carpool and get to your destination.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            if (availableRides.isEmpty()) {
                Text("No available rides at the moment.")
            } else {
                LazyColumn {
                    items(availableRides) { ride ->
                        RideItem(
                            ride = ride,
                            onClick = {}
                        )
                    }
                }
            }
        }
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