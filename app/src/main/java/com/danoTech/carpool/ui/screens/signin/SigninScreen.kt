package com.danoTech.carpool.ui.screens.signin

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.ui.screens.components.ButtonWithLoader
import com.danoTech.carpool.ui.screens.components.EmailField
import com.danoTech.carpool.ui.screens.components.ErrorText
import com.danoTech.carpool.ui.screens.components.PasswordField
import com.danoTech.carpool.ui.screens.components.RepeatPasswordField

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
        Text(
            text = "Signup",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
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
        EmailField(value = uiState.email.trim(), onValueChanged = { signupViewModel.onEmailChanged(it) })

        Spacer(modifier = Modifier.height(20.dp))
        PasswordField(value = uiState.password.trim(), onValueChanged = { signupViewModel.onPasswordChanged(it) })

        Spacer(modifier = Modifier.height(20.dp))
        RepeatPasswordField(value = uiState.confirmPassword.trim(), onValueChanged = { signupViewModel.onConfirmPasswordChanged(it) })

        Spacer(modifier = Modifier.height(20.dp))
        ButtonWithLoader(
            textBeforeLoading = "Register",
            textAfterLoading = "Registering",
            isLoading = uiState.isLoading,
            onClick = {
                signupViewModel.onSignUpClick(openAndPopUp)
            }
        )
    }
}