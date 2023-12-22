package com.cb.bookmrk.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cb.bookmrk.core.model.data.Group

@Composable
fun CollectionRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Surface(
        color = Color.Transparent,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(text = text)
            if (isSelected) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
            }
        }
    }
}

@Composable
fun CollectionRow(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
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
fun GroupRow(
    group: Group,
    onAddClick: (groupId: Long) -> Unit,
    onEditClick: (groupId: Long) -> Unit
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
                ClickableIcon(icon = Icons.Rounded.MoreHoriz, onClick = { onEditClick(group.id) })
            }
            Divider()
        }

    }
}

@Composable
fun GroupRow(group: Group) {
    Surface(
        color = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.outline
    ) {
        Column {
            Text(
                text = group.title,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
            Divider()
        }

    }
}

@Composable
fun ClickableIcon(
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
