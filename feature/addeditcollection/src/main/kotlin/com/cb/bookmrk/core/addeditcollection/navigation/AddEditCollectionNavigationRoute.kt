package com.cb.bookmrk.core.addeditcollection.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.cb.bookmrk.core.addeditcollection.AddEditCollectionRoute

const val addEditCollectionNavigationRoute = "add_edit_collection_route"

private const val groupIdArg = "group_id"
private const val collectionIdArg = "collection_id"

fun NavController.navigateToAddEditCollection(
    groupId: Long? = null,
    collectionId: Long? = null,
    navOptions: NavOptions? = null
) {
    navigate(
        "$addEditCollectionNavigationRoute?$groupIdArg=$groupId&$collectionIdArg=$collectionId",
        navOptions
    )
}

fun NavGraphBuilder.addEditCollectionScreen(
    onNavigationClick: () -> Unit,
    onCreatedUpdatedSuccess: () -> Unit,
    onDeletedSuccess: () -> Unit,
) {
    composable(
        "$addEditCollectionNavigationRoute?$groupIdArg={$groupIdArg}&$collectionIdArg={$collectionIdArg}"
    ) {
        AddEditCollectionRoute(
            onNavigationClick = onNavigationClick,
            onCreatedUpdatedSuccess = onCreatedUpdatedSuccess,
            onDeletedSuccess = onDeletedSuccess
        )
    }
}

internal class AddEditCollectionArgs(val groupId: Long?, val collectionId: Long?) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                savedStateHandle.get<String>(groupIdArg)?.toLongOrNull(),
                savedStateHandle.get<String>(collectionIdArg)?.toLongOrNull()
            )
}
