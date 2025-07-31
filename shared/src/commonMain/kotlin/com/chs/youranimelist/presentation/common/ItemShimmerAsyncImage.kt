package com.chs.youranimelist.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ShimmerImage(
    modifier: Modifier = Modifier,
    url: String?,
    color: Color = Color.LightGray
) {
    val context = LocalPlatformContext.current
    var isLoading by remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier
            .shimmer(visible = isLoading),
        placeholder = ColorPainter(color),
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        onSuccess = { isLoading = false },
        onError = { isLoading = false },
        error = ColorPainter(Color.LightGray)
    )
}