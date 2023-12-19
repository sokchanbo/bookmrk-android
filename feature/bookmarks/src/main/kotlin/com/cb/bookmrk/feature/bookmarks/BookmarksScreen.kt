package com.cb.bookmrk.feature.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Bookmark

@Composable
internal fun BookmarksRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val bookmarks by viewModel.bookmarks.collectAsState()

    BookmarksScreen(
        modifier = modifier,
        onNavigationClick = onNavigationClick,
        bookmarks = bookmarks
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookmarksScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    bookmarks: List<Bookmark>
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = "All Bookmarks",
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick
        )
        LazyColumn {
            items(bookmarks) { bookmark ->
                BookmarkRow(bookmark = bookmark)
            }
        }
    }
}

@Composable
private fun BookmarkRow(bookmark: Bookmark) {
    Surface(
        color = Color.Transparent
    ) {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(bookmark.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(MaterialTheme.shapes.large)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = bookmark.title)
                    Text(
                        text = bookmark.link,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    IconText(icon = Icons.Outlined.Inbox, text = "Unsorted")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "16 Dec 2023",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Divider()
        }
    }
}

@Composable
private fun IconText(
    icon: ImageVector,
    text: String
) {
    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant,
        LocalTextStyle provides MaterialTheme.typography.labelMedium
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
            Text(text = text)
        }
    }
}
