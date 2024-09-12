package com.danoTech.carpool.ui.screens.offer_ride

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerDocked(
    showDatePicker: Boolean,
    departureTime: String = "",
    onDepartureTimeClicked: (String) -> Unit,
    onShowDatePicker: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = departureTime,
            onValueChange = {
                onDepartureTimeClicked(it)
            },
            label = { Text("Pick Departure time.") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { onShowDatePicker(!showDatePicker) }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}