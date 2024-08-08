package com.danoTech.carpool.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun ProfileScreen(
    onBackClicked: () -> Unit = {},
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = profileViewModel.userProfile.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = uiState?.profilePictureUrl,
            contentDescription = "User profile picture",
            modifier = Modifier
                .size(120.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = uiState!!.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        // Add other profile details here, like phone number, email, etc.
        Spacer(modifier = Modifier.height(32.dp))
        // Add ride history or other sections here
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CarpoolTheme {
        ProfileScreen()
    }
}