package com.danoTech.carpool.ui.screens.request_ride

  //noinspection UsingMaterialAndMaterial3Libraries
  //noinspection UsingMaterialAndMaterial3Libraries
  //noinspection UsingMaterialAndMaterial3Libraries
  import android.Manifest
  import android.annotation.SuppressLint
  import android.content.pm.PackageManager
  import android.location.Geocoder
  import android.location.Location
  import android.os.Build
  import androidx.activity.compose.BackHandler
  import androidx.activity.compose.rememberLauncherForActivityResult
  import androidx.activity.result.contract.ActivityResultContracts
  import androidx.annotation.RequiresApi
  import androidx.compose.foundation.layout.Box
  import androidx.compose.foundation.layout.Column
  import androidx.compose.foundation.layout.fillMaxHeight
  import androidx.compose.foundation.layout.fillMaxSize
  import androidx.compose.foundation.layout.padding
  import androidx.compose.material.icons.Icons
  import androidx.compose.material.icons.filled.Home
  import androidx.compose.material.icons.filled.Person
  import androidx.compose.material3.ExperimentalMaterial3Api
  import androidx.compose.material3.ModalBottomSheet
  import androidx.compose.material3.OutlinedTextField
  import androidx.compose.material3.SheetValue
  import androidx.compose.material3.Text
  import androidx.compose.material3.rememberModalBottomSheetState
  import androidx.compose.runtime.Composable
  import androidx.compose.runtime.LaunchedEffect
  import androidx.compose.runtime.collectAsState
  import androidx.compose.runtime.getValue
  import androidx.compose.runtime.mutableStateOf
  import androidx.compose.runtime.remember
  import androidx.compose.runtime.rememberCoroutineScope
  import androidx.compose.runtime.setValue
  import androidx.compose.ui.Alignment
  import androidx.compose.ui.Modifier
  import androidx.compose.ui.graphics.vector.ImageVector
  import androidx.compose.ui.platform.LocalContext
  import androidx.compose.ui.res.stringResource
  import androidx.compose.ui.unit.dp
  import androidx.core.app.ActivityCompat
  import androidx.hilt.navigation.compose.hiltViewModel
  import com.danoTech.carpool.R
  import com.danoTech.carpool.ui.Routes
  import com.google.android.gms.location.LocationServices
  import com.google.android.gms.tasks.CancellationTokenSource
  import kotlinx.coroutines.launch
  import java.util.Locale

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavScreen(Routes.Home.route, "Home", Icons.Default.Home)
    object Profile : BottomNavScreen(Routes.Profile.route, "Profile", Icons.Default.Person)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MapScreenWithSearch(
    city: String,
    country: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    onNavItemClicked: (String) -> Unit,
    viewModel: RideRequestViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cancellationTokenSource = remember { CancellationTokenSource() }
    val geocoder = remember { Geocoder(context, Locale.getDefault()) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                getCurrentLocation(
                    fusedLocationClient,
                    onLocationAvailable = {
                        currentLocation = it
                    }
                )
            }
        }
    )
    val rideUiState = viewModel.rideRequestUiState.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getCurrentLocation(
                fusedLocationClient,
                onLocationAvailable = {
                    currentLocation = it
                }
            )
        }
    }

    if (rideUiState.isSearchingForRide) {
        RideSearchDialog(
            message = rideUiState.searchMessage,
            isError = rideUiState.isErrorSearch,
            onDismissRequest = {}
        )
    }

    BackHandler {
        onBack()
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        MapScreenWithModalBottomSheet(
            city,
            country,
            modifier = modifier,
            viewModel,
            currentLocation,
            geocoder,
            onNavItemClicked = {
                onNavItemClicked(it)
            }
        )
    } else {
        MapScreenWithBottomSheetScaffold(
            city,
            country,
            modifier = modifier,
            viewModel,
            currentLocation,
            geocoder
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
    viewModel: RideRequestViewModel = hiltViewModel(),
    currentLocation: Location?,
    geocoder: Geocoder,
    onNavItemClicked: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    val carpoolOptionText by remember { mutableStateOf("") }
    var isInputActive by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.collectAsState().value
    val rideRequestState = viewModel.rideRequestUiState.collectAsState().value

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
        currentLocation?.let { location ->
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address = addresses?.firstOrNull()
            val locationName = address?.featureName ?: ""

            Box {
                MapDisplay(
                    viewModel = viewModel,
                    country = country,
                    currentLocation = location,
                    modifier = Modifier.fillMaxHeight(.55f)
                )
            }

            CarpoolScreen(
                modifier = Modifier.fillMaxHeight(.5f),
                pickupLocation = locationName,
                estimatedTime = rideRequestState.estimatedTime,
                numberOfSeats = rideRequestState.numberOfSeats,
                price = rideRequestState.price,
                destination = uiState.destination,
                onNavItemClick = {
                    onNavItemClicked(it)
                },
                onDestinationChanged = {
                    viewModel.onDestinationChanged(it)
                },
                onSearchCarPool = {
                    viewModel.searchForCarpool()
                }
            )
        }
    }

    val availableCars = viewModel.availableCars.collectAsState().value
    if (availableCars.isNotEmpty()) {
        Text("Available Cars:")
        availableCars.forEach { car ->
            Text("- ${car.name}")
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
    viewModel: RideRequestViewModel,
    currentLocation: Location?,
    geocoder: Geocoder
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var carpoolOptionText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

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

    currentLocation?.let { location ->
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val address = addresses?.firstOrNull()

        val locationName = address?.getAddressLine(0) ?: ""

        MapDisplay(
            viewModel = viewModel,
            country = country,
            currentLocation = location,
        )
    }
}