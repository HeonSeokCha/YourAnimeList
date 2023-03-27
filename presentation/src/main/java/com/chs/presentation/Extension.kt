package com.chs.presentation

import androidx.compose.ui.graphics.Color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

enum class Season {
    WINTER,
    SPRING,
    SUMMER,
    FALL
}