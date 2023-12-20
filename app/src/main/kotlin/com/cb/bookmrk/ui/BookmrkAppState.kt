package com.cb.bookmrk.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cb.bookmrk.feature.bookmarks.navigation.bookmarksNavigationRoute
import com.cb.bookmrk.feature.home.navigation.homeNavigationRoute

@Composable
fun rememberBookmrkAppState(
    navController: NavHostController = rememberNavController()
): BookmrkAppState =
    remember(navController) {
        BookmrkAppState(navController)
    }

@Stable
class BookmrkAppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowFloatingActionButton: Boolean
        @Composable get() = currentDestination?.route?.contains(
            homeNavigationRoute,
            ignoreCase = true
        ) == true || currentDestination?.route?.contains(
            bookmarksNavigationRoute, ignoreCase = true
        ) == true
}
