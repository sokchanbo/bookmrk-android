package com.cb.bookmrk.feature.addgroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkButton
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun AddGroupRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onCreatedGroup: () -> Unit,
    viewModel: AddGroupViewModel = hiltViewModel()
) {
    AddGroupScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        onCreateGroupClick = {
            viewModel.createGroup(it)
            onCreatedGroup()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddGroupScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onCreateGroupClick: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = stringResource(R.string.new_group),
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick
        )
        BookmrkOutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text(text = stringResource(uiR.string.enter_title)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.weight(1f))
        BookmrkButton(
            text = stringResource(uiR.string.create_group),
            onClick = {
                onCreateGroupClick(title)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding()
                .imePadding()
        )
    }
}
