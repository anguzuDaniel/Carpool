package com.danoTech.carpool.ui.screens.driver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danoTech.carpool.R
import com.danoTech.carpool.ui.PageWithBackButton
import com.danoTech.carpool.ui.screens.components.TextInputWithLabel

@Composable
fun RegisterDriverPage(
    onBackClicked: () -> Unit,
    registerDriverViewModel: RegisterDriverViewModel = hiltViewModel()
) {
    val uiState = registerDriverViewModel.driverState.collectAsState().value

    PageWithBackButton(title = "Register Driver", onBackButtonClicked = onBackClicked) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.register_driver),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextInputWithLabel(
                labelText = stringResource(R.string.name),
                value = uiState.firstName,
                onValueChanged = { registerDriverViewModel.onFirstNameChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextInputWithLabel(
                labelText = stringResource(R.string.name),
                value = uiState.lastName,
                onValueChanged = { registerDriverViewModel.onFirstNameChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextInputWithLabel(
                labelText = stringResource(R.string.email),
                value = uiState.email,
                onValueChanged = { registerDriverViewModel.onEmailChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    registerDriverViewModel.registerDriver()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.register))
            }
        }
    }
}