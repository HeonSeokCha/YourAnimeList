package com.chs.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val String.color
    get() = Color(android.graphics.Color.parseColor(this))


val Int?.isNotEmptyValue
    get() = this != null || this != 0

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

fun Modifier.collapsingHeaderController(
    maxOffsetPx: Float,
    firstVisibleItemIndexProvider: () -> Int,
    onScroll: (currentOffsetY: Float) -> Unit,
): Modifier = composed {
    val scrollListener by rememberUpdatedState(newValue = onScroll)

    val connection = remember {
        object : NestedScrollConnection {
            var lastNotifiedValue = 0f
            var currentOffsetPx = 0f

            fun maybeNotify(value: Float) {
                if (lastNotifiedValue != value) {
                    lastNotifiedValue = value
                    scrollListener(value)
                }
            }

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val firstVisibleIndex = firstVisibleItemIndexProvider()
                currentOffsetPx = (currentOffsetPx + delta).coerceAtMost(0f)

                val isOffsetInAllowedLimits = currentOffsetPx >= -maxOffsetPx

                fun setCurrentOffsetAndNotify() {
                    currentOffsetPx = currentOffsetPx.coerceAtLeast(-maxOffsetPx)
                    maybeNotify(currentOffsetPx)
                }

                fun calculateOffsetAndNotify(): Offset =
                    if (isOffsetInAllowedLimits) {
                        setCurrentOffsetAndNotify()
                        Offset(0f, delta)
                    } else {
                        maybeNotify(currentOffsetPx)
                        Offset.Zero
                    }

                val isScrollingUpWhenHeaderIsDecreased = delta < 0 && firstVisibleIndex == 0
                val isScrollingDownWhenHeaderIsIncreased = delta > 0 && firstVisibleIndex == 0

                return when {
                    isScrollingUpWhenHeaderIsDecreased || isScrollingDownWhenHeaderIsIncreased -> {
                        calculateOffsetAndNotify()
                    }

                    else -> Offset.Zero
                }
            }
        }
    }

    nestedScroll(connection)
}

class CollapsingHeaderState(topInset: Dp) {
    val maxHeaderCollapse = MAX_HEADER_COLLAPSE
    val headerMaxHeight = topInset + MAX_HEADER_HEIGHT
    var headerHeight: Dp by mutableStateOf(headerMaxHeight)

    private val headerElevation by derivedStateOf {
        if (headerHeight > headerMaxHeight - MAX_HEADER_COLLAPSE) 0.dp else 2.dp
    }

    fun findHeaderElevation(isSharedProgressRunning: Boolean): Dp =
        if (isSharedProgressRunning) 0.dp else headerElevation

    companion object{
        val MAX_HEADER_COLLAPSE = 120.dp
        val MAX_HEADER_HEIGHT = 450.dp
    }
}

@Composable
@Stable
fun rememberCollapsingHeaderState(key: Any, topInset: Dp) = remember(key1 = key) {
    CollapsingHeaderState(topInset = topInset)
}