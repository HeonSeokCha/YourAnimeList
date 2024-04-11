package com.chs.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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