package com.danoTech.carpool.ui.screens.login

import com.google.firebase.auth.FirebaseUser
import java.lang.Error

data class LoginUiState(
    val email: String = "",
    var password: String = "",
    val hasMessage: Boolean = false,
    val isSignInSuccess: Boolean = false,
    val message: String = "",
    var isLoading: Boolean = false,
    val error: String? = null
)