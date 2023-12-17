package com.cb.bookmrk.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Group

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
                CollectionRow(icon = Icons.Rounded.Cloud, text = "All bookmarks")
            }
            item {
                CollectionRow(icon = Icons.Rounded.Sort, text = "Unsorted")
            }

            for (group in groups) {
                item {
                    GroupRow(group = group, onAddClick = onAddGroupClick)
                }
                items(group.collections) { collection ->
                    CollectionRow(icon = Icons.Rounded.Folder, text = collection.name)
                }
            }
        }
    }
}

@Composable
private fun CollectionRow(
    icon: ImageVector,
    text: String
) {
    Surface(
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(text = text)
        }
    }
}

@Composable
private fun GroupRow(
    group: Group,
    onAddClick: (groupId: Long) -> Unit
) {
    Surface(
        color = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.outline
    ) {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = group.title,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                ClickableIcon(
                    icon = Icons.Rounded.Add,
                    onClick = {
                        onAddClick(group.id)
                    }
                )
                ClickableIcon(icon = Icons.Rounded.KeyboardArrowRight, onClick = {})
                ClickableIcon(icon = Icons.Rounded.MoreHoriz, onClick = {})
            }
            Divider()
        }

    }
}

@Composable
private fun ClickableIcon(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}
