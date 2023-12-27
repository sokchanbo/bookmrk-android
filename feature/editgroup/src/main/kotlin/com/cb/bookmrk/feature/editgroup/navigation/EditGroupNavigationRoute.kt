package com.cb.bookmrk.feature.editgroup.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.feature.editgroup.EditGroupRoute

const val editGroupNavigationRoute = "edit_group_route"

const val groupIdArg = "group_id"

fun NavController.navigateToEditGroup(groupId: Long, navOptions: NavOptions? = null) {
    navigate("$editGroupNavigationRoute/$groupId", navOptions)
}

fun NavGraphBuilder.editGroupScreen(
    onNavigationClick: () -> Unit,
    onCreateGroupClick: () -> Unit,
    onDeletedGroup: () -> Unit,
) {
    composable(
        "$editGroupNavigationRoute/{$groupIdArg}"
    ) {
        EditGroupRoute(
            onNavigationClick = onNavigationClick,
            onCreateGroupClick = onCreateGroupClick,
            onDeletedGroup = onDeletedGroup
        )
    }
}

internal class EditGroupArgs(val groupId: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<String>(groupIdArg)!!.toLong())
}
