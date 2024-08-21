package com.danoTech.carpool.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageWithBackButton(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onBackButtonClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
       topBar = {
           TopAppBar(
               title = {
                   Text(
                       text = title
                   )
               },
               navigationIcon = {
                   IconButton(onClick = onBackButtonClicked) {
                       Icon(
                           imageVector = icon,
                           contentDescription = "Back button"
                       )
                   }
               }
           )
       }, modifier = modifier
    ) { innerPadding ->
        content(innerPadding)
    }
}