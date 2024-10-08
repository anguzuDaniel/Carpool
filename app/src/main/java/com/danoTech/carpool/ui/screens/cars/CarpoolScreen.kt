package com.danoTech.carpool.ui.screens.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.ui.screens.map.OngoingCarpoolStatus
import com.danoTech.carpool.ui.screens.request_ride.RideConfirmationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpoolScreen(
    modifier: Modifier = Modifier,
    destination: String,
    pickupLocation: String = "",
    estimatedTime: String = "",
    numberOfSeats: Int = 0,
    price: String = "",
    onNavItemClick: (String) -> Unit = {},
    onDestinationChanged: (String) -> Unit = {},
    onSearchCarPool: (String) -> Unit = {},
    availableCars: List<Car>,
    isInCarpool: Boolean = false,
    onCancelCarpool: () -> Unit = {}
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val tabs = listOf("Find ride", "Offer ride")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val cornerRadius = 16.dp

    BottomSheetScaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                if (isInCarpool) {
                    OngoingCarpoolStatus(
                        pickupLocation = pickupLocation,
                        destination = destination,
                        onCancelCarpool = onCancelCarpool
                    )
                } else {
                    RideConfirmationScreen(
                        pickupLocation = pickupLocation,
                        numberOfSeats = numberOfSeats,
                        estimatedTime = estimatedTime,
                        price = price,
                        destination = destination,
                        onConfirmRide = {},
                        onDestinationChanged = {
                            onDestinationChanged(it.lowercase())
                        },
                        onSearchCarPool = {
                            onSearchCarPool(it)
                        }
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(
            topStart = cornerRadius,
            topEnd = cornerRadius
        ),
        sheetPeekHeight = 400.dp,
        sheetSwipeEnabled = false,
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = sheetState
        )
    ) {

    }
}
