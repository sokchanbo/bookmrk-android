package com.cb.bookmrk.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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
}
