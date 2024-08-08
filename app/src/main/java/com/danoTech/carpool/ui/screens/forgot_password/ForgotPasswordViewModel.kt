package com.danoTech.carpool.ui.screens.forgot_password

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.CarPoolViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val accountService: AccountService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState())
    val uiState = _uiState.value

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }

    fun sendResetPasswordLink(email: String) {
        viewModelScope.launch {
            accountService.sendRecoveryEmail(email)
        }
    }

    fun onPassword(obbCode: String ,newPassword: String) {
        viewModelScope.launch {
            val result = accountService.changePassword(obbCode, newPassword)

            if (result) {
                _uiState.value = _uiState.value.copy(
                    message = "Password reset successfully. Please login",
                    hasMessage = true
                )
            }

            if (!result) {
                _uiState.value = _uiState.value.copy(
                    message = "Password reset unsuccessful. Something went wrong. Please try again",
                    hasMessage = true
                )
            }
        }
    }
}
