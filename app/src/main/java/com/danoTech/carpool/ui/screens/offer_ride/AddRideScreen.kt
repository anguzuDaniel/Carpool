package com.danoTech.carpool.ui.screens.offer_ride

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.R
import com.danoTech.carpool.ui.PageWithBackButton
import com.danoTech.carpool.ui.screens.components.ButtonWithLoader
import com.danoTech.carpool.ui.screens.components.ErrorText
import com.danoTech.carpool.ui.screens.components.NumberInputWithLabel
import com.danoTech.carpool.ui.screens.components.TextInputWithLabel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddRideScreen(
    modifier: Modifier = Modifier,
    viewModel: RideOfferViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    PageWithBackButton(
        title = "Offer ride",
        onBackButtonClicked = onBackClicked
    ) { innerPadding ->
        AddRideForm(
            viewModel = viewModel,
            uiState = uiState,
            innerPadding = innerPadding
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddRideForm(
    viewModel: RideOfferViewModel,
    uiState: OfferedRideUiState,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        item {
            Column {
              AnimatedVisibility(visible = uiState.hasError || uiState.hasMessage) {
                  ErrorText(text = uiState.message)
              }

                TextInputWithLabel(
                    labelText = "Car number plate",
                    placeholder = R.string.number_plate,
                    value = uiState.name,
                    onValueChanged = { viewModel.onNumberPlate(it) },
                )

                TextInputWithLabel(
                    labelText = "Model",
                    placeholder = R.string.feilder,
                    value = uiState.model,
                    onValueChanged = { viewModel.onAddModel(it) },
                )

                TextInputWithLabel(
                    labelText = "Make",
                    placeholder = R.string.toyota,
                    value = uiState.make,
                    onValueChanged = { viewModel.onAddMake(it) },
                )

                TextInputWithLabel(
                    labelText = "Driver's name",
                    placeholder = R.string.john_doe,
                    value = uiState.driverName,
                    onValueChanged = { viewModel.onAddDriversName(it) },
                )

              TextInputWithLabel(
                  labelText = "Pickup Location",
                  placeholder = R.string.gayaza,
                  value = uiState.pickupLocation,
                  onValueChanged = { viewModel.addPickUpLocation(it) },
              )

              TextInputWithLabel(
                  labelText = "Destination",
                  value = uiState.destination,
                  placeholder = R.string.kasanaga,
                  onValueChanged = { viewModel.addDestination(it) },
              )

              NumberInputWithLabel(
                  labelText = "Seats Available",
                  value = uiState.seatsAvailable.toString(),
                  onValueChanged = { viewModel.addNumberOfSeats(it.toIntOrNull() ?: 0) }
              )

              DatePickerDocked(
                  departureTime = uiState.date,
                  onDepartureTimeClicked = {
                      viewModel.updateSelectedDate(it)
                  }
              )

              NumberInputWithLabel(
                  labelText = "Price (Optional)",
                  value = uiState.price,
                  onValueChanged = { viewModel.addPrice(it.toIntOrNull() ?: 0) },
              )

              ButtonWithLoader(
                  textBeforeLoading = "Save Offer",
                  textAfterLoading = "Saving...",
                  isLoading = uiState.loading,
                  onClick = viewModel::offerRide,
                  modifier = Modifier.fillMaxWidth()
              )
            }
        }
    }
}