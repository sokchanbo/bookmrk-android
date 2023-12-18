package com.cb.bookmrk.feature.addbookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkButton
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.designsystem.component.CircularProgressDialog

@Composable
internal fun AddBookmarkRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    viewModel: AddBookmarkViewModel = hiltViewModel()
) {
    val addBookmarkUiState by viewModel.addBookmarkUiState.collectAsState()

    AddBookmarkScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        onAddBookmarkClick = viewModel::createBookmark,
        addBookmarkUiState=addBookmarkUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddBookmarkScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onAddBookmarkClick: (String) -> Unit,
    addBookmarkUiState: AddBookmarkUiState?
) {

    var link by remember { mutableStateOf("") }

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
                .imePadding()
        ) {
            BookmrkOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = link,
                onValueChange = { link = it },
                placeholder = { Text(text = stringResource(R.string.enter_link)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            BookmrkButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_bookmark),
                onClick = { onAddBookmarkClick(link) }
            )
        }
    }
}
