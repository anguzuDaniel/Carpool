package com.danoTech.carpool.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.CarPoolViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val email: String
        get() = _uiState.value.email

    private val password: String
        get() = _uiState.value.password

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            accountService.authenticate(email, password)
            _uiState.value = _uiState.value.copy(
                isSignInSuccess = true,
                hasMessage = true,
                message = "Login successfully! kindly wait as we redirect you."
            )
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "Login successfully! kindly wait as we redirect you."
            )
        }
    }
}
