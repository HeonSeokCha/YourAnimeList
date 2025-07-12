package com.chs.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

val String.color
    get() = Color(this.toColorInt())
val Int?.isNotEmptyValue
    get() = this != null && this != 0

val String?.isNotEmptyValue
    get() = this != null && this != ""

val Int?.toCommaFormat
    @SuppressLint("DefaultLocale")
    get() = String.format("%,d", this ?: 0)

@Composable
fun Int.pxToDp(): Dp {
    (this / LocalDensity.current.density).dp.run {
        chsLog(this.toString())
        return this
    }
}

fun isHrefContent(desc: String): Boolean {
    if (desc.startsWith("https://anilist.co/")) {
        return true
    }

    if (desc.startsWith("https://")) {
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

fun chsLog(message: String?) {
    Log.e("CHS_123", message.toString())
}