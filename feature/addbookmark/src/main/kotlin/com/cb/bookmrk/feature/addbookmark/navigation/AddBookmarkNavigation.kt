package com.cb.bookmrk.feature.addbookmark.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.addbookmark.AddBookmarkRoute

const val addBookmarkNavigationRoute = "add_bookmark_route"

private const val collectionIdArg = "collection_id"

fun NavController.navigateToAddBookmark(
    collectionId: Long?,
    navOptions: NavOptions? = null
) {
    navigate("$addBookmarkNavigationRoute?$collectionIdArg=$collectionId", navOptions)
}

fun NavGraphBuilder.addBookmarkScreen(
    onNavigationClick: () -> Unit,
    onAddedBookmark: () -> Unit
) {
    composable("$addBookmarkNavigationRoute?$collectionIdArg={$collectionIdArg}") {
        AddBookmarkRoute(
            onNavigationClick = onNavigationClick,
            onAddedBookmark = onAddedBookmark
        )
    }
}

internal class AddBookmarkArgs(val collectionId: Long?) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<String>(collectionIdArg)?.toLongOrNull())
}
