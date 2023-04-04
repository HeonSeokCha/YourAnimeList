package com.chs.presentation

import androidx.compose.ui.graphics.Color

val String.color
    get() = Color(android.graphics.Color.parseColor(this))


val Int?.isNotEmptyValue
    get() = this != null || this != 0

enum class SearchWidgetState {
    OPENED,
    CLOSED
}