package com.danoTech.carpool.ui

sealed class Routes(val route: String) {
    data object Intro : Routes("Intro")
    data object Login : Routes("Login")
    data object Signup : Routes("Signup")
    data object ForgotPassword : Routes("ForgotPassword")
    data object Profile : Routes("Profile")
}