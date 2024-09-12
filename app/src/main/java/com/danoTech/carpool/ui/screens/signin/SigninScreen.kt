package com.danoTech.carpool.ui.screens.signin

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.R
import com.danoTech.carpool.ui.screens.components.ButtonWithLoader
import com.danoTech.carpool.ui.screens.components.EmailField
import com.danoTech.carpool.ui.screens.components.ErrorText
import com.danoTech.carpool.ui.screens.components.OtpCodeInput
import com.danoTech.carpool.ui.screens.components.PasswordField
import com.danoTech.carpool.ui.screens.components.RepeatPasswordField
import com.danoTech.carpool.ui.screens.components.TextFieldPhoneNumber
import com.danoTech.carpool.ui.screens.components.TextFieldWithLabel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun SignupScreen(
    openAndPopUp: (String, String) -> Unit,
    onSignupClick: () -> Unit = {},
    signupViewModel: SignupViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val uiState = signupViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier.padding(vertical = 40.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        if (uiState.isVerifyingCode) {
            OtpCodeInput(
                value = uiState.verificationId,
                onValueChanged = signupViewModel::onVerificationCodeChanged
            )

            Spacer(modifier = Modifier.height(20.dp))
            ButtonWithLoader(
                textBeforeLoading = "Verify Code",
                textAfterLoading = "Verifying Code...",
                isLoading = uiState.isLoading,
                onClick = signupViewModel::onVerifyCodeClicked
            )
        } else {
            Text(
                text = "Signup",
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodySmall
                )

                ClickableText(
                    text = AnnotatedString("Sign in here"),
                    modifier = Modifier,
                    onClick = {
                        onSignupClick()
                    },
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default,
                        color = MaterialTheme.colorScheme.primary,
                        fontStyle = MaterialTheme.typography.bodySmall.fontStyle
                    )
                )
            }

            AnimatedVisibility(visible = uiState.isCreateAccountSuccess) {
                ErrorText(text = uiState.message)
            }

            AnimatedVisibility(visible = uiState.isCreateAccountError) {
                ErrorText(text = uiState.message)
            }

            LaunchedEffect(key1 = uiState.isCreateAccountSuccess) {
                if (uiState.isCreateAccountSuccess) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            EmailField(
                value = uiState.email.trim(),
                onValueChanged = { signupViewModel.onEmailChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldPhoneNumber(
                value = uiState.phoneNumber.trim(),
                onValueChanged = { signupViewModel.onPhoneNumberChanged(it) },
                onCountryCodeChanged = { signupViewModel.onCountryCodeChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithLabel(
                icon = Icons.Default.Person,
                labelText = stringResource(id = R.string.first_name),
                value = uiState.firstName.trim(),
                onValueChanged = { signupViewModel.onFirstNameChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldWithLabel(
                icon = Icons.Default.Person,
                labelText = stringResource(id = R.string.last_name),
                value = uiState.lastName.trim(),
                onValueChanged = { signupViewModel.onLastNameChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            PasswordField(
                value = uiState.password.trim(),
                onValueChanged = { signupViewModel.onPasswordChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            RepeatPasswordField(
                value = uiState.confirmPassword.trim(),
                onValueChanged = { signupViewModel.onConfirmPasswordChanged(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))
            ButtonWithLoader(
                textBeforeLoading = "Register",
                textAfterLoading = "Registering",
                isLoading = uiState.isLoading,
                onClick = {
                    signupViewModel.onSignUpClick(openAndPopUp)

                    signupViewModel.sendVerificationCode(
                        number = uiState.countryCode + uiState.phoneNumber,
                        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                    // This callback will be invoked in two situations:
                                    // 1 - Instant verification. In some cases the phone number can be instantly
                                    //     verified without needing to send or enter a verification code.
                                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                                    //     detect the incoming verification SMS and perform verification without
                                    //     user action.
                                    Log.d(TAG, "onVerificationCompleted:$credential")
                                    signupViewModel.signInWithPhoneAuthCredential(credential)
                                }

                                override fun onVerificationFailed(e: FirebaseException) {
                                    // This callback is invoked in an invalid request for verification is made,
                                    // for instance if the the phone number format is not valid.
                                    Log.w(TAG, "onVerificationFailed", e)

                                    when (e) {
                                        is FirebaseAuthInvalidCredentialsException -> {
                                            // Invalid request
                                        }

                                        is FirebaseTooManyRequestsException -> {
                                            // The SMS quota for the project has been exceeded
                                        }

                                        is FirebaseAuthMissingActivityForRecaptchaException -> {
                                            // reCAPTCHA verification attempted with null Activity
                                        }
                                    }
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken,
                                ) {
                                    // The SMS verification code has been sent to the provided phone number, we
                                    // now need to ask the user to enter the code and then construct a credential
                                    // by combining the code with a verification ID.
                                    Log.d(TAG, "onCodeSent:$verificationId")

                                    // Save verification ID and resending token so we can use them later
                                    signupViewModel.onVerificationIdChanged(verificationId)
//                                        resendToken = token
                                }
                            }
                    )
                }
            )
        }
    }
}
