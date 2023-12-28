package com.cb.bookmrk.core.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.cb.bookmrk.core.designsystem.component.BookmrkModalBottomSheet
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCollectionModalBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit,
    groups: List<Group>,
    onItemClick: (Collection) -> Unit,
    selectedCollection: Collection?
) {
    BookmrkModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        LazyColumn {
            for (group in groups) {
                item {
                    GroupRow(group = group)
                }
                items(group.collections) { collection ->
                    CollectionRow(
                        icon = Icons.Rounded.Folder,
                        text = collection.name,
                        onClick = { onItemClick(collection) },
                        isSelected = selectedCollection?.id == collection.id
                    )
                }
            }
        }
    }
}
