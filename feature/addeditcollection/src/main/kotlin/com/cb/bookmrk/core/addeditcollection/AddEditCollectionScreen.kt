package com.cb.bookmrk.core.addeditcollection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkButton
import com.cb.bookmrk.core.designsystem.component.BookmrkModalBottomSheet
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkSelectionOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun AddEditCollectionRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onCreatedUpdatedSuccess: () -> Unit,
    onDeletedSuccess: () -> Unit,
    viewModel: AddEditCollectionViewModel = hiltViewModel()
) {

    val collection by viewModel.collection.collectAsState()
    val groups by viewModel.groups.collectAsState()

    AddEditCollectionScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        collection = collection,
        groups = groups,
        onCreateUpdateClick = { collectionName, isPrivate, groupId ->
            if (viewModel.addEditCollectionArgs.collectionId != null && groupId != null) {
                viewModel.updateCollection(collectionName, groupId)
            } else {
                viewModel.createCollection(collectionName, isPrivate)
            }
            onCreatedUpdatedSuccess()
        },
        onConfirmDeleteClick = {
            viewModel.deleteCollection()
            onDeletedSuccess()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddEditCollectionScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    collection: Collection?,
    groups: List<Group>,
    onCreateUpdateClick: (collectionName: String, isPrivate: Boolean, groupId: Long?) -> Unit,
    onConfirmDeleteClick: () -> Unit
) {

    val focusRequest = remember { FocusRequester() }

    val sheetState = rememberModalBottomSheetState()

    var collectionName by remember(collection?.name) {
        mutableStateOf(
            TextFieldValue(
                text = collection?.name.orEmpty(),
                selection = TextRange(collection?.name?.length ?: 0)
            )
        )
    }
    var isPrivate by remember { mutableStateOf(false) }

    var selectedGroup by remember(collection?.group) {
        mutableStateOf(collection?.group)
    }

    var showGroupModalBottomSheet by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = stringResource(
                if (collection == null) R.string.new_collection else uiR.string.collection
            ),
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick,
            actions = {
                if (collection != null) {
                    IconButton(onClick = { showDeleteConfirmationDialog = true }) {
                        Icon(imageVector = Icons.Outlined.DeleteOutline, contentDescription = null)
                    }
                }
            }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BookmrkOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequest),
                value = collectionName,
                onValueChange = { collectionName = it }
            )

            if (collection != null) {
                BookmrkSelectionOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = selectedGroup?.title.orEmpty(),
                    onClick = { showGroupModalBottomSheet = true }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string._private))
                Switch(checked = isPrivate, onCheckedChange = { isPrivate = it })
            }

            Spacer(modifier = Modifier.weight(1f))
            BookmrkButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    if (collection == null) {
                        R.string.create_collection
                    } else {
                        uiR.string.save_changes
                    }
                ),
                onClick = {
                    onCreateUpdateClick(collectionName.text, isPrivate, selectedGroup?.id)
                },
                enabled = collectionName.text.isNotBlank()
            )

            if (showGroupModalBottomSheet) {
                GroupModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showGroupModalBottomSheet = false },
                    groups = groups,
                    selectedGroup = selectedGroup
                )
            }

            if (showDeleteConfirmationDialog) {
                DeleteConfirmationDialog(
                    onDismissRequest = { showDeleteConfirmationDialog = false },
                    onDeleteClick = {
                        showDeleteConfirmationDialog = false
                        onConfirmDeleteClick()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    groups: List<Group>,
    selectedGroup: Group?
) {
    BookmrkModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        LazyColumn {
            items(groups) { group ->
                GroupRow(group = group, isSelected = group.id == selectedGroup?.id)
            }
        }
    }
}

@Composable
private fun GroupRow(group: Group, isSelected: Boolean) {
    Surface(
        color = Color.Transparent,
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = group.title)
            if (isSelected) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.delete_collection)) },
        text = { Text(text = stringResource(R.string.delete_collection_message)) },
        confirmButton = {
            TextButton(
                onClick = onDeleteClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = stringResource(uiR.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(uiR.string.cancel))
            }
        }
    )
}
