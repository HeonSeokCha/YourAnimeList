package com.chs.youranimelist.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorScheme = lightColors(
    primary = Red500,
    primaryVariant = Pink80,
    secondary = Red500
)

@Composable
fun YourAnimeListTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = DarkColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}