package com.cb.bookmrk.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.model.data.HomeScreenClickType
import com.cb.bookmrk.core.ui.CollectionRow
import com.cb.bookmrk.core.ui.GroupRow
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onAddGroupClick: (groupId: Long) -> Unit,
    onEditClick: (groupId: Long) -> Unit,
    onCollectionClick: (HomeScreenClickType, collectionId: Long?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val groups by viewModel.groups.collectAsState()

    HomeScreen(
        modifier = modifier,
        groups = groups,
        onAddGroupClick = onAddGroupClick,
        onEditClick = onEditClick,
        onCollectionClick = onCollectionClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    groups: List<Group>,
    onAddGroupClick: (groupId: Long) -> Unit,
    onEditClick: (groupId: Long) -> Unit,
    onCollectionClick: (HomeScreenClickType, collectionId: Long?) -> Unit
) {
    /*val isExpandedMap = remember(groups) {
        List(groups.size) { index -> index to true }
            .toMutableStateMap()
    }*/

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(text = "Bookmrk")
        LazyColumn {
            item {
                CollectionRow(
                    icon = Icons.Outlined.Cloud,
                    text = stringResource(uiR.string.all_bookmarks),
                    onClick = { onCollectionClick(HomeScreenClickType.AllBookmarks, null) }
                )
            }
            item {
                CollectionRow(
                    icon = Icons.Outlined.Sort,
                    text = stringResource(uiR.string.unsorted),
                    onClick = { onCollectionClick(HomeScreenClickType.Unsorted, null) }
                )
            }

            for (i in groups.indices) {
                item {
                    GroupRow(
                        group = groups[i],
                        onAddClick = onAddGroupClick,
                        onEditClick = onEditClick
                    )
                }

                items(groups[i].collections) { collection ->
                    CollectionRow(
                        icon = Icons.Outlined.Folder,
                        text = collection.name,
                        onClick = {
                            onCollectionClick(HomeScreenClickType.Collection, collection.id)
                        }
                    )
                }
            }
        }
    }
}
