package com.cb.bookmrk.feature.bookmarks.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.core.model.data.HomeScreenClickType
import com.cb.bookmrk.feature.bookmarks.BookmarksRoute

const val bookmarksNavigationRoute = "bookmarks_route"

private const val homeScreenClickTypeArg = "home_screen_click_type"
private const val collectionIdArg = "group_id"

fun NavController.navigateToBookmarks(
    homeScreenClickType: HomeScreenClickType,
    collectionId: Long?,
    navOptions: NavOptions? = null
) {
    navigate(
        "$bookmarksNavigationRoute?$homeScreenClickTypeArg=$homeScreenClickType&$collectionIdArg=$collectionId",
        navOptions
    )
}

fun NavGraphBuilder.bookmarksScreen(
    onNavigationClick: () -> Unit,
) {
    composable(
        "$bookmarksNavigationRoute?$homeScreenClickTypeArg={$homeScreenClickTypeArg}&$collectionIdArg={$collectionIdArg}"
    ) {
        BookmarksRoute(
            onNavigationClick = onNavigationClick
        )
    }
}

internal class BookmarksArgs(
    val homeScreenClickType: HomeScreenClickType,
    val collectionId: Long?
) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                HomeScreenClickType.valueOf(savedStateHandle.get<String>(homeScreenClickTypeArg)!!),
                savedStateHandle.get<String>(collectionIdArg)?.toLongOrNull()
            )
}
