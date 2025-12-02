package com.chs.youranimelist.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEvent.SwipeEdge
import com.chs.youranimelist.presentation.main.MainScreen

fun String.color(): Color {
    val color = removePrefix("#")
    val parsedColor = when (color.length) {
        6 -> color.toLong(16) or 0x00000000FF000000 // Add alpha = 255 if not provided
        8 -> color.toLong(16) // Includes alpha channel
        else -> throw IllegalArgumentException("Unknown color format: $this")
    }
    return Color(parsedColor)
}

val Int?.isNotEmptyValue
    get() = this != null && this != 0

val String?.isNotEmptyValue
    get() = this != null && this != ""

fun Int?.toCommaFormat(): String = this?.let {
    it.toString().reversed().chunked(3).joinToString(",").reversed()
} ?: ""

@Composable
fun Int.pxToDp(): Dp {
    (this / LocalDensity.current.density).dp.run {
        return this
    }
}

fun isHrefContent(desc: String): Boolean {
    if (desc.startsWith("https://")) {
        return true
    }

    if (desc.startsWith("https://anilist.co/")) {
        return true
    }

    return false
}

fun getIdFromLink(
    link: String,
    onAnime: (id: Int) -> Unit,
    onChara: (id: Int) -> Unit,
    onBrowser: (String) -> Unit
) {
    if (!link.startsWith("https://anilist.co/")) return onBrowser(link)

    val type = link.split("https://anilist.co/")[1]

    if (type.startsWith("character")) {
        onChara(type.split("character/")[1].split("/")[0].toInt())
        return
    }

    if (type.startsWith("anime")) {
        onAnime(type.split("anime/")[1].split("/")[0].toInt())
        return
    }
}

val NavigationTransitionSpec = NavDisplay.transitionSpec {
    slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(targetOffsetX = { -it })
} + NavDisplay.popTransitionSpec {
    slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it })
} + NavDisplay.predictivePopTransitionSpec {
    slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it })
}

fun <T : Any> defaultPredictivePopTransitionSpec2():
        AnimatedContentTransitionScope<Scene<T>>.(@SwipeEdge Int) -> ContentTransform = {
    ContentTransform(
        fadeIn(animationSpec = tween(700)),
        fadeOut(animationSpec = tween(700))
    )
}

fun chsLog(message: String?) {
    println(message)
}