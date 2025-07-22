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
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ShimmerImage(
    modifier: Modifier = Modifier,
    url: String?,
    color: Color = Color.LightGray
) {
    var isLoading by remember { mutableStateOf(true) }
    AsyncImage(
        modifier = modifier
            .placeholder(visible = isLoading),
        placeholder = ColorPainter(color),
        model = ImageRequest.Builder(LocalContext.current)
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