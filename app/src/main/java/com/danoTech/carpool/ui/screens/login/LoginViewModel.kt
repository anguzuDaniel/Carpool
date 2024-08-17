package com.danoTech.carpool.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.ui.Routes
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
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

    fun login(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                accountService.authenticate(email, password)
                _uiState.value = _uiState.value.copy(
                    isSignInSuccess = true,
                    hasMessage = true,
                    message = "Login successful! Redirecting..."
                )

                openAndPopUp(Routes.Login.route, Routes.Home.route)
            } catch (e: FirebaseAuthInvalidUserException) {
                _uiState.value = _uiState.value.copy(
                    isSignInSuccess = false,
                    hasMessage = true,
                    message = "No account found with this email. Please sign up first."
                )
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _uiState.value = _uiState.value.copy(
                    isSignInSuccess = false,
                    hasMessage = true,
                    message = "Invalid password. Please try again."
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSignInSuccess = false,
                    hasMessage = true,
                    message = "Login failed: ${e.localizedMessage}. Please try again."
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
