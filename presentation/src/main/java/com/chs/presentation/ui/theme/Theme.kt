package com.chs.presentation.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.chs.presentation.ui.theme.Red500

private val DarkColorScheme = lightColorScheme(
    primary = Red500,
    secondary = Red500
)

@Composable
fun YourAnimeListTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}