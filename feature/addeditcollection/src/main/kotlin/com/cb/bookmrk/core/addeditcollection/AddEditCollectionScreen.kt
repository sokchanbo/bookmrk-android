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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkButton
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar

@Composable
internal fun AddEditCollectionRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onCreatedUpdatedSuccess: () -> Unit,
    viewModel: AddEditCollectionViewModel = hiltViewModel()
) {
    AddEditCollectionScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        onCreateUpdateClick = { collectionName, isPrivate ->
            viewModel.createCollection(collectionName,isPrivate)
            onCreatedUpdatedSuccess()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddEditCollectionScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onCreateUpdateClick: (collectionName: String, isPrivate: Boolean) -> Unit,
) {

    val focusRequest = remember { FocusRequester() }

    var collectionName by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = stringResource(R.string.new_collection),
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick
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
                text = stringResource(R.string.create_collection),
                onClick = {
                    onCreateUpdateClick(collectionName, isPrivate)
                },
                enabled = collectionName.isNotBlank()
            )
        }
    }
}
