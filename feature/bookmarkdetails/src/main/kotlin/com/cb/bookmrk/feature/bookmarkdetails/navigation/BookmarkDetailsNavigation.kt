package com.cb.bookmrk.feature.bookmarkdetails.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.bookmarkdetails.BookmarkDetailsRoute

const val bookmarkDetailsNavigationRoute = "bookmark_details_route"

private const val bookmarkIdArg = "bookmark_id"

fun NavController.navigateToBookmarkDetails(bookmarkId: Long, navOptions: NavOptions? = null) {
    navigate("$bookmarkDetailsNavigationRoute/$bookmarkId", navOptions)
}

fun NavGraphBuilder.bookmarkDetailsScreen(
    onNavigationClick: () -> Unit,
) {
    composable("$bookmarkDetailsNavigationRoute/{$bookmarkIdArg}") {
        BookmarkDetailsRoute(onNavigationClick = onNavigationClick)
    }
}

internal class BookmarkDetailsArgs(val bookmarkId: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<String>(bookmarkIdArg)!!.toLong())
}
