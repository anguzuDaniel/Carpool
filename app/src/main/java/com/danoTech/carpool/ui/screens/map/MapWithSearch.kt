package com.danoTech.carpool.ui.screens.map

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
  import androidx.compose.foundation.layout.Arrangement
  import androidx.compose.foundation.layout.Box
  import androidx.compose.foundation.layout.Row
  import androidx.compose.foundation.layout.fillMaxWidth
  import androidx.compose.foundation.layout.padding
  import androidx.compose.material.icons.Icons
  import androidx.compose.material.icons.filled.Notifications
  import androidx.compose.material.icons.twotone.Menu
  import androidx.compose.material3.Icon
  import androidx.compose.material3.IconButton
  import androidx.compose.material3.IconButtonDefaults
  import androidx.compose.material3.MaterialTheme
  import androidx.compose.material3.Surface
  import androidx.compose.runtime.Composable
  import androidx.compose.runtime.LaunchedEffect
  import androidx.compose.runtime.getValue
  import androidx.compose.runtime.mutableStateOf
  import androidx.compose.runtime.remember
  import androidx.compose.runtime.setValue
  import androidx.compose.ui.Alignment
  import androidx.compose.ui.Modifier
  import androidx.compose.ui.platform.LocalContext
  import androidx.compose.ui.unit.dp
  import androidx.core.app.ActivityCompat
  import androidx.hilt.navigation.compose.hiltViewModel
  import com.danoTech.carpool.ui.screens.request_ride.RideRequestViewModel
  import com.google.android.gms.location.LocationServices
  import java.util.Locale


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
    viewModel: RideRequestViewModel = hiltViewModel(),
    openDrawerNavigation: () -> Unit,
    onSearchPool: (String) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
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

    BackHandler {
        onBack()
    }

    Surface {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            MapScreenWithModalBottomSheet(
                country,
                modifier = modifier,
                viewModel,
                currentLocation,
                geocoder,
                onNavItemClicked = {
                    onNavItemClicked(it)
                },
                onSearchPool = onSearchPool
            )
        } else {
            MapScreenWithBottomSheetScaffold(
                country,
                modifier = modifier,
                viewModel,
                currentLocation,
                geocoder,
                onNavItemClicked = {
                    onNavItemClicked(it)
                },
                onSearchPool = onSearchPool
            )
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = openDrawerNavigation,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(
                    onClick = openDrawerNavigation,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}