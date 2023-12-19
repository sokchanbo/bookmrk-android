package com.cb.bookmrk.feature.bookmarks.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.bookmarks.BookmarksRoute

const val bookmarksNavigationRoute = "bookmarks_route"

private const val collectionIdArg = "group_id"
fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    navigate(bookmarksNavigationRoute, navOptions)
}

fun NavGraphBuilder.bookmarksScreen(
    onNavigationClick: ()->Unit,
) {
    composable(bookmarksNavigationRoute) {
        BookmarksRoute(
            onNavigationClick = onNavigationClick
        )
    }
}

internal class BookmarksArgs(val collectionId: Long?) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<String>(collectionIdArg)?.toLongOrNull())
}
