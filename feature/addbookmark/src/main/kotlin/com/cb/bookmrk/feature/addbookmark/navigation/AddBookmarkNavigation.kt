package com.cb.bookmrk.feature.addbookmark.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.addbookmark.AddBookmarkRoute

const val addBookmarkNavigationRoute = "add_bookmark_route"

fun NavController.navigateToAddBookmark(navOptions: NavOptions? = null) {
    navigate(addBookmarkNavigationRoute, navOptions)
}

fun NavGraphBuilder.addBookmarkScreen(
    onNavigationClick: () -> Unit,
    onAddedBookmark: () -> Unit
) {
    composable(addBookmarkNavigationRoute) {
        AddBookmarkRoute(
            onNavigationClick = onNavigationClick,
            onAddedBookmark = onAddedBookmark
        )
    }
}
