package com.cb.bookmrk.feature.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.cb.bookmrk.core.common.extension.formatAsString
import com.cb.bookmrk.core.designsystem.component.BookmrkTopAppBar
import com.cb.bookmrk.core.model.data.Bookmark
import com.cb.bookmrk.core.model.data.Collection
import com.cb.bookmrk.core.model.data.HomeScreenClickType
import com.cb.bookmrk.core.ui.R as uiR

@Composable
internal fun BookmarksRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    onSetCollectionId: (Long?) -> Unit,
    onEditCollectionClick: (collectionId: Long) -> Unit,
    onItemClick: (Bookmark) -> Unit,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val bookmarks by viewModel.bookmarks.collectAsState()
    val collection by viewModel.collection.collectAsState()

    DisposableEffect(Unit) {
        onSetCollectionId(viewModel.bookmarksArgs.collectionId)
        onDispose { onSetCollectionId(null) }
    }

    BookmarksScreen(
        modifier = modifier,
        homeScreenClickType = viewModel.bookmarksArgs.homeScreenClickType,
        onNavigationClick = onNavigationClick,
        bookmarks = bookmarks,
        collection = collection,
        onEditCollectionClick = onEditCollectionClick,
        onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookmarksScreen(
    modifier: Modifier = Modifier,
    homeScreenClickType: HomeScreenClickType,
    onNavigationClick: () -> Unit,
    bookmarks: List<Bookmark>,
    collection: Collection?,
    onEditCollectionClick: (collectionId: Long) -> Unit,
    onItemClick: (Bookmark) -> Unit
) {

    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val toolbarTitle = remember(homeScreenClickType, collection) {
        collection?.name
            ?: when (homeScreenClickType) {
                HomeScreenClickType.AllBookmarks -> context.getString(uiR.string.all_bookmarks)
                HomeScreenClickType.Unsorted -> context.getString(uiR.string.unsorted)
                HomeScreenClickType.Collection -> ""
            }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BookmrkTopAppBar(
            text = toolbarTitle,
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigationClick = onNavigationClick,
            scrollBehavior = scrollBehavior,
            actions = {
                if (homeScreenClickType == HomeScreenClickType.Collection && collection != null) {
                    IconButton(onClick = { onEditCollectionClick(collection.id) }) {
                        Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = null)
                    }
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(bookmarks) { bookmark ->
                BookmarkRow(
                    homeScreenClickType = homeScreenClickType,
                    bookmark = bookmark,
                    onClick = onItemClick
                )
            }
            item { Spacer(modifier = Modifier.navigationBarsPadding()) }
        }
    }
}

@Composable
private fun BookmarkRow(
    homeScreenClickType: HomeScreenClickType,
    bookmark: Bookmark,
    onClick: (Bookmark) -> Unit
) {
    Surface(
        color = Color.Transparent,
        onClick = { onClick(bookmark) }
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
                        .clip(MaterialTheme.shapes.large),
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = bookmark.title)
                    Text(
                        text = bookmark.link,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (homeScreenClickType == HomeScreenClickType.AllBookmarks) {
                        if (bookmark.collection != null) {
                            IconText(
                                icon = Icons.Outlined.Folder,
                                text = bookmark.collection!!.name
                            )
                        } else {

                            IconText(
                                icon = Icons.Outlined.Inbox,
                                text = stringResource(uiR.string.unsorted)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = bookmark.createdDate.formatAsString(),
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
