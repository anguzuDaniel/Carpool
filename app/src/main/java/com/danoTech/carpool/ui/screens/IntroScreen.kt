package com.danoTech.carpool.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danoTech.carpool.R
import com.danoTech.carpool.ui.theme.CarpoolTheme

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    onSigningButtonClick: () -> Unit = {},
    onSignupButtonClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.welcome),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = stringResource(R.string.intro_message),
                    fontSize = 20.sp,
                    modifier = Modifier.alpha(.3f),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo"
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = onSigningButtonClick,
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .absolutePadding(bottom = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primary,
                        Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.go_to_sign_in))
                }

                OutlinedButton(
                    onClick = onSignupButtonClick,
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .absolutePadding(top = 5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.no_account_yet_msg),
                        color = if (isSystemInDarkTheme())  Color.White else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreviewLight() {
    CarpoolTheme {
        IntroScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreviewDark() {
    CarpoolTheme(
        darkTheme = true
    ) {
        IntroScreen()
    }
}