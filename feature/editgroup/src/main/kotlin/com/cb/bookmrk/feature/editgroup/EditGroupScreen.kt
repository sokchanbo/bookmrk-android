package com.cb.bookmrk.feature.editgroup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.designsystem.component.BookmrkButton
import com.cb.bookmrk.core.designsystem.component.BookmrkOutlinedTextField
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Group
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun EditGroupRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onCreateGroupClick: () -> Unit,
    onDeletedGroup: () -> Unit,
    viewModel: EditGroupViewModel = hiltViewModel()
) {
    val group by viewModel.group.collectAsState()
    val collectionCount by viewModel.collectionCount.collectAsState()
    val showDeleteGroupError by viewModel.showDeleteGroupError.collectAsState()

    EditGroupScreen(
        modifier = modifier,
        onNavigationClick = { title ->
            viewModel.updateGroup(title = title)
            onNavigationClick()
        },
        group = group,
        showDeleteGroupError = showDeleteGroupError,
        onCreateGroupClick = onCreateGroupClick,
        onRemoveGroupClick = {
            viewModel.removeGroup()
            if (collectionCount <= 0) {
                onDeletedGroup()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditGroupScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: (String) -> Unit,
    group: Group?,
    showDeleteGroupError: Boolean,
    onCreateGroupClick: () -> Unit,
    onRemoveGroupClick: () -> Unit
) {

    var title by remember(group?.title) {
        mutableStateOf(group?.title.orEmpty())
    }

    BackHandler {
        onNavigationClick(title)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = "Group",
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = { onNavigationClick(title) }
        )
        if (group != null) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                BookmrkOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(text = stringResource(uiR.string.enter_title))
                    }
                )

                Spacer(modifier = Modifier)

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BookmrkButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(uiR.string.create_group),
                        onClick = onCreateGroupClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                        )
                    )
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onRemoveGroupClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(text = stringResource(R.string.remove_group))
                    }
                }

                if (showDeleteGroupError) {
                    ErrorMessage(text = stringResource(R.string.remove_group_error_message))
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
