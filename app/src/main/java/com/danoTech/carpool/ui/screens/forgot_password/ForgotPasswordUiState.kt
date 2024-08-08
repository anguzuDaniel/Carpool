package com.danoTech.carpool.ui.screens.forgot_password

data class ForgotPasswordUiState(
    val email: String = "",
    val newPassword: String = "",
    val hasMessage: Boolean = false,
    val message: String = ""
)
