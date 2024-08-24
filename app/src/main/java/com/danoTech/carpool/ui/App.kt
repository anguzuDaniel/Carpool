package com.danoTech.carpool.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.danoTech.carpool.BottomNavScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun App(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerContentList = listOf(
        BottomNavScreen.OfferRide,
        BottomNavScreen.Profile,
        BottomNavScreen.RideRequest
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Anguzu Daniel", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                drawerContentList.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.title)
                        },
                        label = { Text(text = item.title) },
                        selected = false,
                        onClick = { navController.navigate(item.route) }
                    )
                }
                HorizontalDivider()
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "about")
                    },
                    label = { Text(text = "About") },
                    selected = false,
                    onClick = { }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    },
                    label = { Text(text = "Logout") },
                    selected = false,
                    onClick = { }
                )
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        NavigateToScreen(
            navController = navController,
            modifier = Modifier,
            openDrawerNavigation = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
    }
}