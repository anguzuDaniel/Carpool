package com.danoTech.carpool.ui.screens.signin

data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isCreateAccountButtonEnabled: Boolean = false,
    val isCreateAccountInProgress: Boolean = false,
    val isCreateAccountSuccess: Boolean = false,
    val isCreateAccountError: Boolean = false,
    var message: String = "",
    var isLoading: Boolean = false
)
