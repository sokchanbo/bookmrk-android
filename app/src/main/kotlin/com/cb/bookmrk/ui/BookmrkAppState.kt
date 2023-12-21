package com.cb.bookmrk.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private val _collectionId = mutableStateOf<Long?>(null)
    val collectionId: State<Long?> get() = _collectionId

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

    fun setNavigationCollectionId(collectionId: Long?) {
        _collectionId.value = collectionId
    }
}
