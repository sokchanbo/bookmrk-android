package com.cb.bookmrk.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmrkTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = Color.Transparent
    )
) {
    MediumTopAppBar(
        modifier = modifier,
        title = {
            Text(text = text)
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmrkTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    text: String,
    navigationIcon: ImageVector,
    onNavigationClick: () -> Unit,
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = Color.Transparent
    ),
    actions: @Composable RowScope.() -> Unit = {}
) {
    MediumTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = text)
        },
        colors = colors,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(imageVector = navigationIcon, contentDescription = null)
            }
        },
        actions = actions
    )
}

