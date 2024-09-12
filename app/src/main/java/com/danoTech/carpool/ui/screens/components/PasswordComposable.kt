package com.danoTech.carpool.ui.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.R

@Composable
fun PasswordField(value: String, onValueChanged: (String) -> Unit, modifier: Modifier = Modifier) {
    PasswordField(value, R.string.password, onValueChanged, modifier)
}

@Composable
fun RepeatPasswordField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, R.string.repeat_password, onValueChanged, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(
                text = stringResource(placeholder),
                style = MaterialTheme.typography.titleMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Lock"
            )
        },
        trailingIcon = {
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(10.dp)
    )
    Spacer(modifier = modifier.height(5.dp))
}