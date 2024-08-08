package com.danoTech.carpool.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danoTech.carpool.ui.screens.IntroScreen
import com.danoTech.carpool.ui.screens.forgot_password.ForgotPasswordScreen
import com.danoTech.carpool.ui.screens.login.LoginPage
import com.danoTech.carpool.ui.screens.profile.ProfileScreen
import com.danoTech.carpool.ui.screens.request_ride.RequestRideScreen
import com.danoTech.carpool.ui.screens.signin.SignupScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun App(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Intro.route
    ) {
        composable(Routes.Intro.route) {
            IntroScreen(
                onSigningButtonClick = {
                    navController.navigate(Routes.Login.route)
                },
                onSignupButtonClick = {
                    navController.navigate(Routes.Signup.route)
                }
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
                }
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
    }
}