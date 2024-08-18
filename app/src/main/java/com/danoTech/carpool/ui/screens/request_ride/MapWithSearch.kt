package com.danoTech.carpool.ui.screens.request_ride

  import android.annotation.SuppressLint
  import android.os.Build
  import androidx.activity.compose.BackHandler
  import androidx.annotation.RequiresApi
  import androidx.compose.foundation.background
  import androidx.compose.foundation.layout.Box
  import androidx.compose.foundation.layout.Column
  import androidx.compose.foundation.layout.fillMaxHeight
  import androidx.compose.foundation.layout.fillMaxSize
  import androidx.compose.foundation.layout.fillMaxWidth
  import androidx.compose.foundation.layout.padding
  import androidx.compose.foundation.shape.RoundedCornerShape
  //noinspection UsingMaterialAndMaterial3Libraries
  import androidx.compose.material.Icon
  import androidx.compose.material.icons.Icons
  import androidx.compose.material.icons.filled.Close
  import androidx.compose.material3.BottomSheetScaffold
  import androidx.compose.material3.ExperimentalMaterial3Api
  import androidx.compose.material3.MaterialTheme
  import androidx.compose.material3.ModalBottomSheet
  import androidx.compose.material3.OutlinedTextField
  import androidx.compose.material3.SheetValue
  import androidx.compose.material3.Tab
  import androidx.compose.material3.TabRow
  import androidx.compose.material3.Text
  import androidx.compose.material3.rememberBottomSheetScaffoldState
  import androidx.compose.material3.rememberModalBottomSheetState
  import androidx.compose.material3.rememberStandardBottomSheetState
  import androidx.compose.runtime.Composable
  import androidx.compose.runtime.LaunchedEffect
  import androidx.compose.runtime.collectAsState
  import androidx.compose.runtime.getValue
  import androidx.compose.runtime.mutableIntStateOf
  import androidx.compose.runtime.mutableStateOf
  import androidx.compose.runtime.remember
  import androidx.compose.runtime.rememberCoroutineScope
  import androidx.compose.runtime.setValue
  import androidx.compose.ui.Alignment
  import androidx.compose.ui.Modifier
  import androidx.compose.ui.draw.clip
  import androidx.compose.ui.graphics.Color
  import androidx.compose.ui.res.stringResource
  import androidx.compose.ui.tooling.preview.Preview
  import androidx.compose.ui.unit.dp
  import androidx.hilt.navigation.compose.hiltViewModel
  import com.danoTech.carpool.R
  import com.danoTech.carpool.ui.theme.CarpoolTheme
  import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MapScreenWithSearch(
    city: String,
    country: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: RideRequestViewModel = hiltViewModel()
) {
    BackHandler {
        onBack()
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        MapScreenWithModalBottomSheet(
            city,
            country,
            modifier = modifier,
            viewModel
        )
    } else {
        MapScreenWithBottomSheetScaffold(
            city,
            country,
            modifier = modifier,
            viewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenWithModalBottomSheet(
    city: String,
    country: String,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    val carpoolOptionText by remember { mutableStateOf("") }
    var isInputActive by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = carpoolOptionText) {
        if (carpoolOptionText.isBlank() && sheetState.currentValue == SheetValue.Expanded) {
            scope.launch { sheetState.partialExpand() }
            isInputActive = false
        } else if (carpoolOptionText.isNotBlank() && sheetState.currentValue != SheetValue.Expanded) {
            scope.launch { sheetState.expand() }
            isInputActive = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            MapDisplay(
                viewModel = viewModel,
                country = country,
                city = city,
                searchText = carpoolOptionText,
                modifier = Modifier.fillMaxHeight(.55f)
            )
        }

        CarpoolScreen(
            modifier = Modifier.fillMaxHeight(.5f),
        )
    }

    val availableCars = viewModel.availableCars.collectAsState().value
    if (availableCars.isNotEmpty()) {
        Text("Available Cars:")
        availableCars.forEach { car ->
            Text("- ${car.name}") // Replace with actual car details
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenWithBottomSheetScaffold(
    city: String,
    country: String,
    modifier: Modifier = Modifier,
    viewModel: RideRequestViewModel
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var carpoolOptionText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { /* Handle dismiss if needed */ },
        sheetState = sheetState
    ) {
        Column(Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = carpoolOptionText,
                onValueChange= { carpoolOptionText = it },
                label = { Text(stringResource(R.string.where_do_you_want_to_go)) },
            )
        }
    }

    MapDisplay(
        viewModel = viewModel,
        country = country,
        city = city,
        searchText = carpoolOptionText
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarpoolScreen(
    modifier: Modifier = Modifier
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val tabs = listOf("Find Pool", "Offer Pool")
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val cornerRadius = 16.dp

    BottomSheetScaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex.intValue,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex.intValue == index,
                            onClick = { selectedTabIndex.intValue = index },
                            text = { Text(text = title) },
                            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedContentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.background(
                                if (selectedTabIndex.intValue == index) MaterialTheme.colorScheme.primary else Color.Transparent
                            )
                        )
                    }
                }

                when (selectedTabIndex.intValue) {
                    0 -> RideConfirmationScreen(
                        pickupLocation = "Kansanga",
                        numberOfSeats = 1,
                        estimatedTime = "200",
                        price = ""
                    ) {

                    }

                    1 -> OfferPoolScreen()
                    else -> {}
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun RideScreen() {
    CarpoolTheme {
        MapScreenWithSearch(city = "", country = "")
    }
}