package com.cb.bookmrk.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cb.bookmrk.core.designsystem.component.BookmrkBackground
import com.cb.bookmrk.feature.addbookmark.navigation.navigateToAddBookmark
import com.cb.bookmrk.navigation.BookmrkNavHost

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookmrkApp(
    appState: BookmrkAppState = rememberBookmrkAppState()
) {

    val collectionId by appState.collectionId

    BookmrkBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            floatingActionButton = {
                if (appState.shouldShowFloatingActionButton) {
                    LargeFloatingActionButton(
                        modifier = Modifier.navigationBarsPadding(),
                        onClick = {
                            appState.navController.navigateToAddBookmark(collectionId)
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                    }
                }
            },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                BookmrkNavHost(appState = appState)
            }
        }
    }
}