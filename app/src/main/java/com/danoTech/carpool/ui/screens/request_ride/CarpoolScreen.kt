package com.danoTech.carpool.ui.screens.request_ride

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
    onSearchCarPool: () -> Unit = {}
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
                RideConfirmationScreen(
                    pickupLocation = pickupLocation,
                    numberOfSeats = numberOfSeats,
                    estimatedTime = estimatedTime,
                    price = price,
                    destination = destination,
                    onConfirmRide = {},
                    onDestinationChanged = {
                        onDestinationChanged(it)
                    },
                    onSearchCarPool = onSearchCarPool
                )
//                TabRow(
//                    selectedTabIndex = selectedTabIndex.intValue,
//                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
//                    contentColor = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .padding(bottom = 16.dp)
//                        .clip(MaterialTheme.shapes.extraLarge)
//                ) {
//                    tabs.forEachIndexed { index, title ->
//                        Tab(
//                            selected = selectedTabIndex.intValue == index,
//                            onClick = { selectedTabIndex.intValue = index },
//                            text = { Text(text = title) },
//                            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
//                            unselectedContentColor = MaterialTheme.colorScheme.primary,
//                            modifier = Modifier.background(
//                                if (selectedTabIndex.intValue == index) MaterialTheme.colorScheme.primary else Color.Transparent
//                            )
//                        )
//                    }
//                }
//
//                when (selectedTabIndex.intValue) {
//
//
//                    1 -> OfferPoolScreen()
//                    else -> {}
//                }
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
    ) { innerPadding ->

    }
}
