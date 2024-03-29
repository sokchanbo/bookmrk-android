package com.cb.bookmrk.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.core.model.data.HomeScreenClickType
import com.cb.bookmrk.feature.home.HomeRoute

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onAddGroupClick: (groupId: Long) -> Unit,
    onEditClick: (groupId: Long) -> Unit,
    onCollectionClick: (HomeScreenClickType, collectionId: Long?) -> Unit
) {
    composable(homeNavigationRoute) {
        HomeRoute(
            onAddGroupClick = onAddGroupClick,
            onEditClick = onEditClick,
            onCollectionClick = onCollectionClick
        )
    }
}
