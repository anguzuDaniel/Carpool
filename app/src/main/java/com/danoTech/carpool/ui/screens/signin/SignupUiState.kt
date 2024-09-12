package com.danoTech.carpool.ui.screens.signin

data class SignupUiState(
    val name: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val isVerifyingCode: Boolean = false,
    val isPhoneNumberVerificationError: Boolean = false,
    val isPhoneNumberVerificationInProgress : Boolean = false,
    val isPhoneNumberVerificationSuccess : Boolean = false,
    val verificationCode: String = "",
    val confirmPassword: String = "",
    val verificationId: String = "",
    val countryCode: String = "",
    val isCreateAccountButtonEnabled: Boolean = false,
    val isCreateAccountInProgress: Boolean = false,
    val isCreateAccountSuccess: Boolean = false,
    val isCreateAccountError: Boolean = false,
    var message: String = "",
    var isLoading: Boolean = false
)
