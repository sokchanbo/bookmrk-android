package com.cb.bookmrk.feature.editbookmark.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.editbookmark.EditBookmarkRoute

const val editBookmarkNavigationRoute = "edit_bookmark_route"

private const val bookmarkIdArg = "bookmark_id"

fun NavController.navigateToEditBookmark(bookmarkId: Long, navOptions: NavOptions? = null) {
    navigate("$editBookmarkNavigationRoute/$bookmarkId", navOptions)
}

fun NavGraphBuilder.editBookmarkScreen(
    onNavigationClick: () -> Unit,
    onUpdatedBookmark: () -> Unit,
) {
    composable("$editBookmarkNavigationRoute/{$bookmarkIdArg}") {
        EditBookmarkRoute(
            onNavigationClick = onNavigationClick,
            onUpdatedBookmark = onUpdatedBookmark
        )
    }
}

internal class EditBookmarkArgs(val bookmarkId: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<String>(bookmarkIdArg)!!.toLong())
}
