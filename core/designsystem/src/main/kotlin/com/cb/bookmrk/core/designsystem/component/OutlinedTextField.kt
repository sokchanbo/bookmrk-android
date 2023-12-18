package com.cb.bookmrk.core.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun BookmrkOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = MaterialTheme.shapes.large,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    helperText: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            shape = shape,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            leadingIcon = leadingIcon,
            isError = isError,
            colors = colors
        )

        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.labelMedium,
            LocalContentColor provides MaterialTheme.colorScheme.error
        ) {
            if (helperText != null && helperText != {}) {
                Box(Modifier.padding(horizontal = 8.dp)) {
                    helperText.invoke()
                }
            }
        }
    }
}

@Composable
fun BookmrkSelectionOutlinedTextField(
    value: String,
    onClick: () -> Unit,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = { BookmrkDropdownIcon() },
    shape: Shape = MaterialTheme.shapes.large,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(OutlinedTextFieldDefaults.MinHeight)
            .border(
                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                shape = shape,
                color = MaterialTheme.colorScheme.outline
            )
            .clip(shape)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier)
        if (leadingIcon != null) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Box(
                    modifier = Modifier.size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    leadingIcon()
                }
            }
        }

        Text(
            text = value.ifEmpty { placeholder ?: "" },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            color = if (value.isEmpty()) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            style = MaterialTheme.typography.bodyLarge
        )
        trailingIcon?.invoke()
        Spacer(modifier = Modifier)
    }
}

@Composable
fun BookmrkDropdownIcon() {
    Icon(
        imageVector = Icons.Rounded.KeyboardArrowRight,
        contentDescription = null,
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
