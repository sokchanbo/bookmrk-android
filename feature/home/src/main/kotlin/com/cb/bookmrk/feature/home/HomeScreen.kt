package com.cb.bookmrk.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.ui.CollectionRow
import com.cb.bookmrk.core.ui.GroupRow

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onAddGroupClick: (groupId: Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val groups by viewModel.groups.collectAsState()

    HomeScreen(
        modifier = modifier,
        groups = groups,
        onAddGroupClick = onAddGroupClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    groups: List<Group>,
    onAddGroupClick: (groupId: Long) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(text = "Bookmrk")
        LazyColumn {
            item {
                CollectionRow(icon = Icons.Rounded.Cloud, text = "All bookmarks", onClick = {})
            }
            item {
                CollectionRow(icon = Icons.Rounded.Sort, text = "Unsorted", onClick = {})
            }

            for (group in groups) {
                item {
                    GroupRow(group = group, onAddClick = onAddGroupClick)
                }
                items(group.collections) { collection ->
                    CollectionRow(icon = Icons.Rounded.Folder, text = collection.name, onClick = {})
                }
            }
        }
    }
}
