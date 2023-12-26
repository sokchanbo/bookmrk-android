package com.cb.bookmrk.feature.bookmarkdetails

import android.content.Intent
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.cb.bookmrk.core.model.data.Bookmark
import kotlinx.coroutines.delay

@Composable
internal fun BookmarkDetailsRoute(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    viewModel: BookmarkDetailsViewModel = hiltViewModel()
) {
    val bookmark by viewModel.bookmark.collectAsState()

    BookmarkDetailsScreen(
        modifier = modifier,
        bookmark = bookmark,
        onNavigationClick = onNavigationClick
    )
}

@Composable
internal fun BookmarkDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    bookmark: Bookmark?,
) {
    val context = LocalContext.current

    var webView by remember { mutableStateOf<WebView?>(null) }

    var progress by remember { mutableFloatStateOf(0f) }

    val progressAnimateAsState by animateFloatAsState(targetValue = progress, label = "Progress")

    var isProgressVisible by remember { mutableStateOf(true) }

    LaunchedEffect(progress) {
        if (progress >= 1f) {
            delay(300)
        }
        isProgressVisible = progress < 1f
    }


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (bookmark != null) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webView = this
                        settings.javaScriptEnabled = true
                        settings.setSupportZoom(false)

                        loadUrl(bookmark.link)

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                progress = newProgress / 100f
                            }
                        }
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean = request?.url?.scheme?.startsWith("http") == false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .statusBarsPadding()
            )
            AnimatedVisibility(visible = isProgressVisible) {
                LinearProgressIndicator(
                    progress = progressAnimateAsState,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            BottomAppBar(
                onBackClick = {
                    if (webView?.canGoBack() == true) {
                        webView?.goBack()
                    } else {
                        onNavigationClick()
                    }
                },
                onShareClick = {
                    val share = Intent.createChooser(
                        Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, bookmark.link)
                            type = "text/html"
                        },
                        null
                    )
                    context.startActivity(share)
                },
                onBrowserClick = {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(bookmark.link)
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun BottomAppBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onBrowserClick: () -> Unit
) {
    Surface(
        tonalElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomAppBarIconButton(
                onClick = onShareClick,
            ) {
                Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
            }
            BottomAppBarIconButton(onClick = onBrowserClick) {
                Icon(painter = painterResource(R.drawable.ic_chrome), contentDescription = null)
            }
            BottomAppBarIconButton(
                onClick = {},
            ) {
                Icon(imageVector = Icons.Outlined.MoreHoriz, contentDescription = null)
            }
        }
    }
}

@Composable
private fun BottomAppBarIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        icon()
    }
}
