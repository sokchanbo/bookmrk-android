package com.cb.bookmrk.feature.editbookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkSelectionOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.designsystem.component.DynamicAsyncImage
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.model.data.UpdateBookmark
import com.cb.bookmrk.core.ui.ChooseCollectionModalBottomSheet
import kotlinx.coroutines.launch
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun EditBookmarkRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onUpdatedBookmark: () -> Unit,
    viewModel: EditBookmarkViewModel = hiltViewModel()
) {
    val bookmark by viewModel.bookmark.collectAsState()
    val groups by viewModel.groups.collectAsState()

    EditBookmarkScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        bookmark = bookmark,
        groups = groups,
        onSaveChangesClick = {
            viewModel.updateBookmark(it)
            onUpdatedBookmark()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditBookmarkScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    bookmark: Bookmark?,
    groups: List<Group>,
    onSaveChangesClick: (UpdateBookmark) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var title by remember(bookmark?.title) {
        mutableStateOf(
            TextFieldValue(
                text = bookmark?.title.orEmpty()
            )
        )
    }
    var description by remember(bookmark?.description) {
        mutableStateOf(bookmark?.description.orEmpty())
    }
    var note by remember(bookmark?.note) { mutableStateOf(bookmark?.note.orEmpty()) }
    var link by remember(bookmark?.link) { mutableStateOf(bookmark?.link.orEmpty()) }
    var selectedCollection by remember(bookmark?.collection) {
        mutableStateOf(bookmark?.collection)
    }
    var showChooseCollectionBottomSheet by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    /*val canScroll = remember(scrollState.isScrollInProgress) {
        scrollState.canScrollForward || scrollState.canScrollBackward
    }*/

    val isValidUrl = remember(link) {
        "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$"
            .toRegex()
            .matches(link)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = "Bookmark",
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick,
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteOutline,
                        contentDescription = null
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
        if (bookmark != null) {
            Column(
                modifier = Modifier.imePadding()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DynamicAsyncImage(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(MaterialTheme.shapes.large),
                            imageUrl = bookmark.imageUrl,
                            contentDescription = null
                        )
                        BookmrkOutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = title,
                            onValueChange = {
                                title = it
                            }
                        )
                    }
                    BookmrkOutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text(text = "Description") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = null
                            )
                        }
                    )
                    BookmrkOutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = note,
                        onValueChange = { note = it },
                        placeholder = { Text(text = "Note") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = null
                            )
                        }
                    )
                    BookmrkOutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = link,
                        onValueChange = { link = it },
                        placeholder = { Text(text = stringResource(uiR.string.enter_link)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Link,
                                contentDescription = null
                            )
                        }
                    )
                    BookmrkSelectionOutlinedTextField(
                        value = selectedCollection?.name.orEmpty(),
                        onClick = { showChooseCollectionBottomSheet = true },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Folder,
                                contentDescription = null
                            )
                        },
                        placeholder = "Choose collection (Optional)"
                    )
                    if (showChooseCollectionBottomSheet) {
                        ChooseCollectionModalBottomSheet(
                            sheetState = sheetState,
                            onDismissRequest = { showChooseCollectionBottomSheet = false },
                            groups = groups,
                            onItemClick = {
                                selectedCollection = if (selectedCollection?.id == it.id) {
                                    null
                                } else {
                                    it
                                }
                                scope.launch {
                                    sheetState.hide()
                                    showChooseCollectionBottomSheet = false
                                }
                            },
                            selectedCollection = selectedCollection,
                        )
                    }
                }
                BottomAppBar(
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.ContentCopy,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
                        }
                    },
                    floatingActionButton = {
                        TextButton(
                            onClick = {
                                onSaveChangesClick(
                                    UpdateBookmark(
                                        id = bookmark.id,
                                        title = title.text,
                                        description = description,
                                        note = note,
                                        link = link,
                                        collectionId = selectedCollection?.id
                                    )
                                )
                            },
                            enabled = isValidUrl && title.text.isNotBlank() && link.isNotBlank()
                        ) {
                            Text(text = stringResource(uiR.string.save_changes))
                        }
                    },
                    tonalElevation = 6.dp,
                )
            }
        }
    }
}
