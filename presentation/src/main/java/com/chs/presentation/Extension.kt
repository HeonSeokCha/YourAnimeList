package com.chs.presentation.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))

val Float.toOffset
    get() = Offset(0f, this)

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}