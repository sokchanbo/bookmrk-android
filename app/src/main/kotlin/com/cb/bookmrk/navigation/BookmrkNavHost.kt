package com.cb.bookmrk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.cb.bookmrk.core.addeditcollection.navigation.addEditCollectionScreen
import com.cb.bookmrk.core.addeditcollection.navigation.navigateToAddEditCollection
import com.cb.bookmrk.feature.addbookmark.navigation.addBookmarkScreen
import com.cb.bookmrk.feature.bookmarks.navigation.bookmarksScreen
import com.cb.bookmrk.feature.bookmarks.navigation.navigateToBookmarks
import com.cb.bookmrk.feature.home.navigation.homeNavigationRoute
import com.cb.bookmrk.feature.home.navigation.homeScreen
import com.cb.bookmrk.ui.BookmrkAppState

@Composable
fun BookmrkNavHost(
    modifier: Modifier = Modifier,
    appState: BookmrkAppState,
    startDestination: String = homeNavigationRoute
) {
    val navController = appState.navController

    NavHost(
        modifier = modifier,
        navController = appState.navController,
        startDestination = startDestination
    ) {
        homeScreen(
            onAddGroupClick = navController::navigateToAddEditCollection,
            onCollectionClick = { homeScreenClickType, collectionId ->
                navController.navigateToBookmarks(
                    homeScreenClickType,
                    collectionId
                )
            }
        )
        addEditCollectionScreen(
            onNavigationClick = navController::popBackStack,
            onCreatedUpdatedSuccess = navController::popBackStack
        )
        addBookmarkScreen(
            onNavigationClick = navController::popBackStack,
            onAddedBookmark = navController::popBackStack
        )
        bookmarksScreen(
            onNavigationClick = navController::popBackStack
        )
    }
}