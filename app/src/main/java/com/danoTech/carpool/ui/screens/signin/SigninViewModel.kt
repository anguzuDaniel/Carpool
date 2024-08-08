package com.danoTech.carpool.ui.screens.signin

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.common.ext.isValidEmail
import com.danoTech.carpool.common.ext.isValidPassword
import com.danoTech.carpool.common.ext.passwordMatches
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.ui.Routes
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val accountService: AccountService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    private val email: String
        get() = _uiState.value.email

    private val password: String
        get() = _uiState.value.password

    /**
     * This function is called when the user changes the email field.
     * @param email The email the user entered.
     * @see SignupUiState
     */
    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    /**
     * This function is called when the user changes the password field.
     * @param password The password the user entered.
     * @see SignupUiState
     */
    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    /**
     * This function is called when the user input in the confirm password field.
     * @param confirmPassword
     * @see SignupUiState
     */
    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    /**
     * This function is called when the user inputs in the first name
     * @param name
     */
    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    /**
     * This function is create account button is clicked
     */
    fun onCreateAccountClicked() {
        _uiState.value = _uiState.value.copy(isCreateAccountInProgress = true)
    }

    /**
     * when account is created successfully
     */
    fun onAccountCreated() {
        _uiState.value = _uiState.value.copy(
            isCreateAccountInProgress = false, isCreateAccountSuccess = true
        )
    }

    /**
     * when account creation fails
     */
    fun onAccountCreationFailed() {
        _uiState.value =
            _uiState.value.copy(isCreateAccountInProgress = false, isCreateAccountError = true)
    }

    /**
     * show when there is an error
     */
    fun onAccountCreationErrorShown() {
        _uiState.value = _uiState.value.copy(isCreateAccountError = false)
    }

    /**
     * show when account creation is successful
     */
    fun onAccountCreationSuccessShown() {
        _uiState.value = _uiState.value.copy(isCreateAccountSuccess = false)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true, message = "Please enter a valid email!"
            )
            return
        }

        if (!password.isValidPassword()) {
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true,
                message  = "Please enter a valid email!"
            )
            return
        }

        if (!password.passwordMatches(uiState.value.confirmPassword)) {
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true,
                message = "Password doesn't match!"
            )
            return
        }

        viewModelScope.launch {
            try {
                accountService.createAccountWithEmailAndPassword(email, password)
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    message = "Account created successfully!"
                )

                openAndPopUp(Routes.Signup.route, Routes.Login.route)
            } catch (e: FirebaseAuthUserCollisionException) {
                _uiState.value = _uiState.value.copy(
                    isCreateAccountError = true,
                    message = "The email address is already in use by another account."
                )
            } catch (error: Exception) {
                _uiState.value = _uiState.value.copy(
                    isCreateAccountError = true,
                    message = "Account creation failed: ${error.message}"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}