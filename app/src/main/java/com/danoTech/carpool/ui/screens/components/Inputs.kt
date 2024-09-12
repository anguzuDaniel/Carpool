package com.danoTech.carpool.ui.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.danoTech.carpool.R
import com.rejowan.ccpc.CCPUtils
import com.rejowan.ccpc.Country
import com.rejowan.ccpc.CountryCodePickerTextField

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
                style = MaterialTheme.typography.titleSmall,
            )
        },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(16.dp))
}

@Composable
fun NumberInputWithLabel(
    labelText: String,
    value: String,
    modifier: Modifier = Modifier,
    placeholder: Int = 0,
    onValueChanged: (String) -> Unit = {},
    onSearchInputClicked: () -> Unit = {},
) {
    Text(
        text = labelText,
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = modifier.height(8.dp))
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text(
                text = placeholder.toString()  ,
                style = MaterialTheme.typography.titleSmall,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
    )
    Spacer(modifier = modifier.height(16.dp))
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
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = modifier.height(5.dp))
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        placeholder = {
            Text(
                text = stringResource(placeholder),
                style = MaterialTheme.typography.titleSmall,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onSearchInputClicked() }),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
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
            autoCorrectEnabled = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
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
        value = value.trim(),
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.email),
                style = MaterialTheme.typography.titleMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        shape = RoundedCornerShape(10.dp)
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun TextFieldWithLabel(
    icon: ImageVector,
    labelText: String,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = value.trim(),
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        shape = RoundedCornerShape(10.dp)
    )
    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun TextFieldPhoneNumber(
    value: String,
    onValueChanged: (String) -> Unit,
    onCountryCodeChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var country by remember {
        mutableStateOf(Country.Uganda)
    }

    if (!LocalInspectionMode.current) {
        CCPUtils.getCountryAutomatically(context = LocalContext.current).let {
            it?.let {
                country = it
            }
        }
    }

    CountryCodePickerTextField(
        modifier = modifier
            .fillMaxWidth(),
        enabled = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(
                text = "Phone Number", style = MaterialTheme.typography.bodyMedium
            )
        },
        showError = true,
        shape = RoundedCornerShape(10.dp),
        onValueChange = { countryCode, value, _ ->
            onCountryCodeChanged(countryCode)
            onValueChanged(value)
        },
        number = value,
        selectedCountry = country
    )

    Spacer(modifier = modifier.height(5.dp))
}

@Composable
fun OtpCodeInput(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = value.trim(),
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.enter_otp_code),
                style = MaterialTheme.typography.titleMedium
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
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