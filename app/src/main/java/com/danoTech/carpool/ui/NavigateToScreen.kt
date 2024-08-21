package com.danoTech.carpool.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danoTech.carpool.ui.screens.IntroScreen
import com.danoTech.carpool.ui.screens.forgot_password.ForgotPasswordScreen
import com.danoTech.carpool.ui.screens.home.HomePage
import com.danoTech.carpool.ui.screens.login.LoginPage
import com.danoTech.carpool.ui.screens.offer_ride.AddRideScreen
import com.danoTech.carpool.ui.screens.profile.ProfileScreen
import com.danoTech.carpool.ui.screens.request_ride.RequestRideScreen
import com.danoTech.carpool.ui.screens.signin.SignupScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigateToScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    openDrawerNavigation: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.RequestRide.route,
        modifier = modifier
    ) {
        composable(Routes.Intro.route) {
            IntroScreen(
                onSigningButtonClick = {
                    navController.navigate(Routes.RequestRide.route)
                },
                onSignupButtonClick = {
                    navController.navigate(Routes.Signup.route)
                },
                modifier = modifier
            )
        }

        composable(Routes.Login.route) {
            LoginPage(
                onForgotPasswordClick = {
                    navController.navigate(Routes.ForgotPassword.route)
                },
                onSignUpClick = {
                    navController.navigate(Routes.Login.route)
                },
                onPopBackStack = { from, to ->
                    navController.navigate(to) {
                        launchSingleTop = true
                        popUpTo(from) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ForgotPassword.route) {
            ForgotPasswordScreen(
                onSendResetPasswordLink = {}
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.RequestRide.route) {
            RequestRideScreen(
                onBack = {
                    navController.popBackStack()
                },
                onClose = {
                    navController.navigate(Routes.RequestRide.route)
                },
                onNavItemClicked = { screen ->
                    navController.navigate(screen) {
                        // Pop all back stack entries
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                openDrawerNavigation = openDrawerNavigation
            )
        }

        composable(Routes.Signup.route) {
            SignupScreen(
                openAndPopUp = { from, to ->
                    navController.navigate(to) {
                        launchSingleTop = true
                        popUpTo(from) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomePage(
                onRideRequestClick = {
                    navController.navigate(Routes.RequestRide.route)
                },
                onProfileClick = { /*TODO*/ },
                onSettingsClick = {
                    navController.navigate(Routes.Profile.route)
                },
                onOfferRideClick = {
                    navController.navigate(Routes.OfferRide.route)
                }
            )
        }

        composable(Routes.OfferRide.route) {
            AddRideScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}