package com.danoTech.carpool.ui.screens.login

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun LoginPage(
    onPopBackStack: (String, String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    loginPageViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val uiState = loginPageViewModel.uiState.collectAsState().value

    Column(
        modifier = modifier.padding(vertical = 60.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodySmall
            )

            ClickableText(
                text = AnnotatedString("Sign up here"),
                modifier = Modifier,
                onClick = {
                    onSignUpClick()
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = MaterialTheme.typography.bodySmall.fontStyle
                )
            )
        }

        AnimatedVisibility(visible = uiState.hasMessage) {
            ErrorText(text = uiState.message)
        }

        Spacer(modifier = Modifier.height(20.dp))
        EmailField(
            value = uiState.email.trim(),
            onValueChanged = {
                loginPageViewModel.onEmailChanged(it)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        PasswordField(
            value = uiState.password.trim(),
            onValueChanged = { loginPageViewModel.onPasswordChanged(it) }
        )

        LaunchedEffect(key1 = uiState.isSignInSuccess) {
            if (uiState.isSignInSuccess) {
                Toast.makeText(
                    context,
                    "Sign in successful",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ButtonWithLoader(
            textBeforeLoading = "Login",
            textAfterLoading = "Logging in",
            isLoading = uiState.isLoading,
            onClick = {
                loginPageViewModel.login { login, requestRide ->
                    onPopBackStack(login, requestRide)
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = {
                onForgotPasswordClick()
            },
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp).align(Alignment.End)
        )
    }
}