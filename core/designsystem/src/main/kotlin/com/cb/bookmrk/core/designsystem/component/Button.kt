package com.cb.bookmrk.core.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookmrkButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        enabled = enabled
    ) {
        Text(text = text)
    }
}
