package com.danoTech.carpool.ui.screens.signin

import androidx.lifecycle.viewModelScope
import com.danoTech.carpool.CarPoolViewModel
import com.danoTech.carpool.common.ext.isValidEmail
import com.danoTech.carpool.common.ext.isValidPassword
import com.danoTech.carpool.common.ext.passwordMatches
import com.danoTech.carpool.model.Profile
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.service.StorageService
import com.danoTech.carpool.ui.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val accountService: AccountService,
    private val storageService: StorageService
) : CarPoolViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    private val email: String
        get() = _uiState.value.email

    private val password: String
        get() = _uiState.value.password

    private val phoneNumber: String
        get() = _uiState.value.phoneNumber

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

    fun onPhoneNumberChanged(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phoneNumber)
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

        if (uiState.value.firstName.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true,
                message = "Please enter your first name!"
            )
            return
        }

        if (uiState.value.lastName.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true,
                message = "Please enter your last name!"
            )
            return
        }

        if (uiState.value.phoneNumber.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                isCreateAccountError = true,
                message = "Please enter your phone number!"
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

                val profile = Profile(
                    phoneNumber = uiState.value.countryCode + uiState.value.phoneNumber,
                    email = uiState.value.email,
                    firstName = uiState.value.firstName,
                    lastName = uiState.value.lastName,
                    profileName = uiState.value.name,
                    profileImageUrl = ""
                )

                storageService.addProfile(profile)

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

    fun onVerificationCodeChanged(verificationCode: String) {
        _uiState.value = _uiState.value.copy(verificationCode = verificationCode)
    }

    fun onVerificationIdChanged(verificationId: String) {
        _uiState.value = _uiState.value.copy(verificationId = verificationId)
    }

    fun initiatePhoneNumberVerification() {
        val auth = FirebaseAuth.getInstance()

        val phoneNumberFormatted = "+${phoneNumber.replace(Regex("[^\\d]"), "")}" // Ensure valid phone number format

        _uiState.value = _uiState.value.copy(isPhoneNumberVerificationInProgress = true)

//        viewModelScope.launch {
//            auth.verifyPhoneNumber(
//                phoneNumberFormatted,
//                60, // Timeout in seconds
//                TimeUnit.SECONDS,
//                requireActivity(),
//                object : OnVerificationStateChangedListener {
//                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                        accountService.signInWithCredential(credential)
//                    }
//
//                    override fun onVerificationFailed(e: FirebaseAuthException) {
//                        _uiState.value = _uiState.value.copy(
//                            isPhoneNumberVerificationInProgress = false,
//                            isPhoneNumberVerificationError = true,
//                            message = "Phone number verification failed: ${e.message}"
//                        )
//                    }
//
//                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                        _uiState.value = _uiState.value.copy(
//                            isPhoneNumberVerificationInProgress = false,
//                            isPhoneNumberVerificationSuccess = true,
//                            verificationId = verificationId
//                        )
//                    }
//                }
//            )
//        }
    }

    fun onVerifyCodeClicked() {
        val verificationId = _uiState.value.verificationId
        val code = _uiState.value.verificationCode

        _uiState.value = _uiState.value.copy(isVerifyingCode = true)

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
    ) {
        viewModelScope.launch {
            try {
                accountService.signInWithCredential(credential)
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    fun sendVerificationCode(
        number: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun onCountryCodeChanged(countryCode: String) {
        _uiState.value = _uiState.value.copy(countryCode = countryCode)
    }

    fun onFirstNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(firstName = name)
    }

    fun onLastNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(lastName = name)
    }
}