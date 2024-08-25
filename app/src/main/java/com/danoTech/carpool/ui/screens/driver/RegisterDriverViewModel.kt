package com.danoTech.carpool.ui.screens.driver


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.service.Driver
import com.danoTech.carpool.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterDriverViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationUiState())
    val registrationState = _registrationState.asStateFlow()

    private val _driverState = MutableStateFlow(DriverUiState())
    val driverState = _driverState.asStateFlow()

    fun registerDriver() = viewModelScope.launch {
        val driver = Driver(
            firstName = _driverState.value.firstName,
            lastName = _driverState.value.lastName,
            email = _driverState.value.email,
            phoneNumber = _driverState.value.phoneNumber
        )

        try {
            _registrationState.value = _registrationState.value.copy(isLoading = true)
            storageService.registerDriver(driver)
            _registrationState.value = _registrationState.value
        } catch (exception: Exception) {
            _registrationState.value = _registrationState.value.copy(
                isRegistered = false, errorMessage = exception.message ?: "Registration failed"
            )
        } finally {
            _registrationState.value = _registrationState.value.copy(isLoading = false)
        }
    }

    fun onFirstNameChanged(name: String) {
        _driverState.value = _driverState.value.copy(firstName = name)
    }

    fun onSecondNameChanged(name: String) {
        _driverState.value = _driverState.value.copy(lastName = name)
    }

    fun onEmailChanged(email: String) {
        _driverState.value = _driverState.value.copy(email = email)
    }
}
