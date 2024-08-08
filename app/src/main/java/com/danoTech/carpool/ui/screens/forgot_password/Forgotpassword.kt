package com.danoTech.carpool.ui.screens.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danoTech.carpool.ui.screens.profile.ProfileScreen
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun ForgotPasswordScreen(
    onSendResetPasswordLink: (String) -> Unit = {},
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val uiState = forgotPasswordViewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Forgot Password", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            label = { Text(text = "Email") },
            value = uiState.email,
            onValueChange = {  }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSendResetPasswordLink(uiState.email) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Send Reset Link")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    CarpoolTheme {
        ForgotPasswordScreen()
    }
}