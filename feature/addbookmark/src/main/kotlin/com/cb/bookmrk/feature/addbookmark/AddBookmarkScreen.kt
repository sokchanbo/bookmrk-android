package com.cb.bookmrk.feature.addbookmark

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DriveFolderUpload
import androidx.compose.material.icons.rounded.AddLink
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkButton
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkSelectionOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.designsystem.component.CircularProgressDialog
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.ui.ChooseCollectionModalBottomSheet
import kotlinx.coroutines.launch
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun AddBookmarkRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onAddedBookmark: () -> Unit,
    viewModel: AddBookmarkViewModel = hiltViewModel()
) {
    val groups by viewModel.groups.collectAsState()
    val collection by viewModel.collection.collectAsState()
    val addBookmarkUiState by viewModel.addBookmarkUiState.collectAsState()

    LaunchedEffect(addBookmarkUiState) {
        if (addBookmarkUiState is AddBookmarkUiState.Success) {
            onAddedBookmark()
        }
    }

    AddBookmarkScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        groups = groups,
        collection = collection,
        onAddBookmarkClick = viewModel::createBookmark,
        addBookmarkUiState = addBookmarkUiState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddBookmarkScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    groups: List<Group>,
    collection: Collection?,
    onAddBookmarkClick: (link: String, collectionId: Long?) -> Unit,
    addBookmarkUiState: AddBookmarkUiState?,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var link by remember { mutableStateOf("") }
    var selectedCollection by remember(collection) { mutableStateOf(collection) }

    var showChooseCollectionBottomSheet by remember { mutableStateOf(false) }

    val isValidLink = remember(link) {
        if (link.isBlank()) true else Patterns.WEB_URL.matcher(link).matches()
    }

    when (addBookmarkUiState) {
        is AddBookmarkUiState.Loading -> CircularProgressDialog()
        is AddBookmarkUiState.Success -> Unit
        is AddBookmarkUiState.Failure -> Unit
        else -> Unit
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = stringResource(R.string.new_bookmark),
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BookmrkOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = link,
                onValueChange = { link = it },
                placeholder = { Text(text = stringResource(uiR.string.enter_link)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.AddLink,
                        contentDescription = null
                    )
                },
                isError = !isValidLink,
                helperText = {
                    if (!isValidLink) {
                        Text(text = "Please enter a valid link.")
                    }
                },
            )
            BookmrkSelectionOutlinedTextField(
                value = selectedCollection?.name ?: "",
                onClick = { showChooseCollectionBottomSheet = true },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.DriveFolderUpload,
                        contentDescription = null
                    )
                },
                placeholder = "Choose collection (Optional)",
                enabled = collection == null
            )
            Spacer(modifier = Modifier.weight(1f))
            BookmrkButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_bookmark),
                onClick = { onAddBookmarkClick(link, selectedCollection?.id) },
                enabled = link.isNotBlank() && isValidLink
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
    }
}
