package com.cb.bookmrk.core.designsystem.component


import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmrkModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    tonalElevation: Dp = 4.dp,
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    content: @Composable ColumnScope.() -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenWidthHeightDp = configuration.screenHeightDp.dp
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues()

    val minHeight = remember(screenWidthHeightDp) { screenWidthHeightDp.div(2.2f) }

    val maxHeight = remember(screenWidthHeightDp) {
        screenWidthHeightDp - statusBarPadding.calculateBottomPadding()
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        windowInsets = windowInsets,
        sheetState = sheetState,
        modifier = modifier.heightIn(min = minHeight, max = maxHeight),
        tonalElevation = tonalElevation,
        content = content
    )
}
