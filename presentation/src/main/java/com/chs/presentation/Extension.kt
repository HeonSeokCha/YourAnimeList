package com.chs.presentation

import androidx.compose.ui.graphics.Color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

enum class Season(
    val rawValue: String
) {
    WINTER("WINTER"),
    SPRING("SPRING"),
    SUMMER("SUMMER"),
    FALL("FALL")
}