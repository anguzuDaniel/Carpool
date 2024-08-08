package com.danoTech.carpool.ui.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.R

@Composable
fun TextInput(
    labelText: String,
    value: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    onValueChanged: (String) -> Unit = {},
    onSearchInputClicked: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    contentDescription = null
                )
            }
        },
        placeholder = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.labelSmall,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false,
            imeAction = ImeAction.Next,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun NumberInputWithLabel(
    @StringRes placeholder: Int = R.string.empty,
    labelText: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
    onSearchInputClicked: () -> Unit = {},
) {
    Text(
        text = labelText,
        style = MaterialTheme.typography.labelSmall,
    )
    Spacer(modifier = modifier.height(5.dp))
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false,
            imeAction = ImeAction.Next,
        ),
        placeholder = {
            Text(
                text = stringResource(placeholder),
                style = MaterialTheme.typography.labelSmall,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun TextInputWithLabel(
    labelText: String,
    value: String,
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int = R.string.empty,
    onValueChanged: (String) -> Unit = {},
    onSearchInputClicked: () -> Unit = {},
) {
    Text(
        text = labelText,
        style = MaterialTheme.typography.labelSmall,
    )
    Spacer(modifier = modifier.height(5.dp))
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false,
            imeAction = ImeAction.Next,
        ),
        placeholder = {
            Text(
                text = stringResource(placeholder),
                style = MaterialTheme.typography.labelSmall,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun ReviewInputWithLabel(
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int = R.string.empty,
    value: String,
    onValueChanged: (String) -> Unit = {},
    onSearchInputClicked: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = true,
            imeAction = ImeAction.Next,
        ),
        placeholder = {
            Text(
                text = stringResource(placeholder),
                style = MaterialTheme.typography.labelSmall,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun EmailField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.email),
                style = MaterialTheme.typography.labelSmall
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun SearchTextField(
    value: TextFieldValue,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    onSearchInputClicked: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.labelSmall,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.Search),
                modifier = Modifier.size(24.dp)
            )
        },
        placeholder = {
            Text(
                text = stringResource(placeholder),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
        modifier = modifier.clickable(onClick = onSearchInputClicked)
    )
}