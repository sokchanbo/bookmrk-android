package com.cb.bookmrk.core.addeditcollection.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.core.addeditcollection.AddEditCollectionRoute

const val addEditCollectionNavigationRoute = "add_edit_collection_route"

private const val groupIdArg = "group_id"

fun NavController.navigateToAddEditCollection(groupId: Long, navOptions: NavOptions? = null) {
    navigate("$addEditCollectionNavigationRoute/$groupId", navOptions)
}

fun NavGraphBuilder.addEditCollectionScreen(
    onNavigationClick: () -> Unit,
    onCreatedUpdatedSuccess: () -> Unit
) {
    composable(
        "$addEditCollectionNavigationRoute/{$groupIdArg}"
    ) {
        AddEditCollectionRoute(
            onNavigationClick = onNavigationClick,
            onCreatedUpdatedSuccess = onCreatedUpdatedSuccess
        )
    }
}

internal class AddEditCollectionArgs(val groupId: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle.get<String>(groupIdArg)!!.toLong())
}
