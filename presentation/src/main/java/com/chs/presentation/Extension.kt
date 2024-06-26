package com.chs.presentation

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.chs.presentation.main.Screen

val String.color
    get() = Color(android.graphics.Color.parseColor(this))

val Int?.isNotEmptyValue
    get() = this != null && this != 0

val String?.isNotEmptyValue
    get() = this != null && this != ""

@Composable
fun Int.pxToDp(): Dp {
    return (this / LocalDensity.current.density).dp
}

fun NavBackStackEntry?.fromRoute(): Screen? {
    return this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                Screen.SearchScreen::class.simpleName -> Screen.SearchScreen
                Screen.SortListScreen::class.simpleName -> Screen.SortListScreen()
                Screen.HomeScreen::class.simpleName -> Screen.HomeScreen
                Screen.AnimeListScreen::class.simpleName -> Screen.AnimeListScreen
                Screen.CharaListScreen::class.simpleName -> Screen.CharaListScreen
                else -> null
            }
        }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}
