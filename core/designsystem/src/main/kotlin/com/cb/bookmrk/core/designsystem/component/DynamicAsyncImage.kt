package com.cb.bookmrk.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import coil.compose.AsyncImagePainter.State
import coil.compose.rememberAsyncImagePainter

@Composable
fun DynamicAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String?,
    //placeholder: Painter = painterResource(R.drawable.core_designsystem_ic_placeholder_default),
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is State.Loading
            isError = state is State.Error
        },
    )
    val isLocalInspection = LocalInspectionMode.current
    Image(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        painter = imageLoader, /*if (isError.not() && !isLocalInspection) imageLoader else placeholder*/
        contentDescription = contentDescription,
    )
    /*Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
       *//* if (isLoading && !isLocalInspection) {
            // Display a progress bar while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }*//*
        Image(
            contentScale = ContentScale.Crop,
            painter = imageLoader *//*if (isError.not() && !isLocalInspection) imageLoader else placeholder*//*,
            contentDescription = contentDescription,
        )
    }*/
}
