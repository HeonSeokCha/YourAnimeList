package com.chs.presentation

import android.annotation.SuppressLint
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

val Int?.toCommaFormat
    @SuppressLint("DefaultLocale")
    get() = String.format("%,d", this ?: 0)

@Composable
fun Int.pxToDp(): Dp {
    return (this / LocalDensity.current.density).dp
}

fun NavBackStackEntry?.fromRoute(): Screen? {
    return this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                Screen.Search::class.simpleName -> Screen.Search
                Screen.SortList::class.simpleName -> Screen.SortList()
                Screen.Home::class.simpleName -> Screen.Home
                Screen.AnimeList::class.simpleName -> Screen.AnimeList
                Screen.CharaList::class.simpleName -> Screen.CharaList
                else -> null
            }
        }
}