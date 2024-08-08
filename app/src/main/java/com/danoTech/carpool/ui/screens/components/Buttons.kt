package com.danoTech.carpool.ui.screens.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danoTech.carpool.R

@Composable
fun ButtonWithLoader(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    textBeforeLoading: String,
    textAfterLoading: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .animateContentSize(
                    animationSpec = (tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    ))
                )
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isLoading) textAfterLoading else textBeforeLoading,
                color = Color.White,
            )
            if (isLoading) {
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/**
 * reusable button
 * provide a string resource for the button name
 * provide a click handler
 */
@Composable
fun CarPoolButton(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    onClicked: () -> Unit = {},
) {
    Button(
        onClick = onClicked,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
fun CarPoolOutlineButton(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    onClicked: () -> Unit = {},
) {
    OutlinedButton(
        onClick = onClicked,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
fun CarPoolImageButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes name: Int,
    onClicked: () -> Unit = {},
) {
    Button(
        onClick = onClicked,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = stringResource(id = name),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

/**
 * FAB for the search button
 * provide a click handler
 * sends your the search screen
 */
@Composable
fun CarPoolFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.Search),
        )
    }
}

/**
 * reusable text input
 */
@Composable
fun CategoryIconButton(
    modifier: Modifier = Modifier,
    description: String,
    @DrawableRes icon: Int,
    @StringRes name: Int,
    onCategoryClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            IconButton(
                onClick = onCategoryClicked,
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = description
                )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/**
 * reusable text input
 * show options for the search screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBusinessButton(
    @StringRes name: Int,
    active: Boolean = false,
    onFilterClick: () -> Unit = {}
) {
    if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

    var selected by remember { mutableStateOf(false) }
    FilterChip(
        selected = selected,
        onClick = {
            selected = !selected
            onFilterClick()
        },
        label = { Text(stringResource(id = name)) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            {}
        }
    )
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors =
        buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}