package com.cb.bookmrk.feature.addgroup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.addgroup.AddGroupRoute

const val addGroupNavigationRoute = "add_group_route"

fun NavController.navigateToAddGroup(navOptions: NavOptions? = null) {
    navigate(addGroupNavigationRoute, navOptions)
}

fun NavGraphBuilder.addGroupScreen(
    onNavigationClick: () -> Unit,
    onCreatedGroup: () -> Unit
) {
    composable(addGroupNavigationRoute) {
        AddGroupRoute(
            onNavigationClick = onNavigationClick,
            onCreatedGroup = onCreatedGroup
        )
    }
}
