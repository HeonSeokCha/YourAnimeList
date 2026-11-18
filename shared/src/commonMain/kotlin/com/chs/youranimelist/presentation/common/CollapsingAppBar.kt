package com.chs.youranimelist.presentation.common

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt

@Stable
class CollapsingToolbarState(initialHeightOffset: Float = 0f) {

    internal var heightOffsetLimit by mutableFloatStateOf(0f)

    internal var heightOffset by mutableFloatStateOf(initialHeightOffset)

    val collapsedFraction: Float
        get() = if (heightOffsetLimit != 0f) heightOffset / heightOffsetLimit else 0f

    internal fun consume(dy: Float): Float {
        val old = heightOffset
        heightOffset = (old + dy).coerceIn(heightOffsetLimit, 0f)
        return heightOffset - old
    }

    internal suspend fun fling(velocity: Float) {
        var last = 0f
        AnimationState(initialValue = 0f, initialVelocity = velocity)
            .animateDecay(exponentialDecay()) {
                val delta = value - last
                last = value
                if (abs(delta - consume(delta)) > 0.5f) cancelAnimation()
            }
    }

    val nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (available.y >= 0f) return Offset.Zero
            return Offset(0f, consume(available.y))
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (available.y <= 0f) return Offset.Zero
            return Offset(0f, consume(available.y))
        }
    }

    companion object {
        val Saver: Saver<CollapsingToolbarState, Float> = Saver(
            save = { it.heightOffset },
            restore = { CollapsingToolbarState(initialHeightOffset = it) }
        )
    }
}

@Composable
fun rememberCollapsingToolbarState(): CollapsingToolbarState {
    return rememberSaveable(saver = CollapsingToolbarState.Saver) { CollapsingToolbarState() }
}

@Composable
fun CollapsingToolbarScaffold(
    state: CollapsingToolbarState = rememberCollapsingToolbarState(),
    expandContent: @Composable () -> Unit = { },
    collapsedContent: @Composable (collapsedFraction: () -> Float) -> Unit,
    isShowTopBar: Boolean = false,
    stickyContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit
) {
    val draggableState = rememberDraggableState { delta -> state.consume(delta) }

    Layout(
        contents = listOf(
            { Column { expandContent() } },
            { Box { stickyContent() } },
            { Column { collapsedContent { state.collapsedFraction } } },
            { Column { content() } },
        ),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(state.nestedScrollConnection)
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = { velocity -> state.fling(velocity) }
            )
    ) { (expandMeasurables, stickyMeasurables, barMeasurables, bodyMeasurables), constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val expandPlaceable = expandMeasurables.first().measure(looseConstraints)
        val stickyPlaceable = stickyMeasurables.first().measure(looseConstraints)
        val barPlaceable = barMeasurables.first().measure(looseConstraints)

        val expandHeight = expandPlaceable.height
        val stickyHeight = stickyPlaceable.height
        val barHeight = barPlaceable.height

        val headerTop = if (isShowTopBar) barHeight else 0
        val pinnedTop = barHeight
        val offsetLimit = -(headerTop + expandHeight - pinnedTop).coerceAtLeast(0).toFloat()
        state.heightOffsetLimit = offsetLimit

        val bodyHeight = (constraints.maxHeight - pinnedTop - stickyHeight).coerceAtLeast(0)
        val bodyPlaceable = bodyMeasurables.first().measure(
            looseConstraints.copy(minHeight = bodyHeight, maxHeight = bodyHeight)
        )

        layout(constraints.maxWidth, constraints.maxHeight) {
            val offset = state.heightOffset.coerceAtLeast(offsetLimit).roundToInt()
            val stickyTop = headerTop + expandHeight + offset

            expandPlaceable.place(0, headerTop + offset)
            bodyPlaceable.place(0, stickyTop + stickyHeight)
            stickyPlaceable.place(0, stickyTop)
            barPlaceable.place(0, 0)
        }
    }
}

@Composable
fun GradientTopBar(
    modifier: Modifier,
    topBarIcon: ImageVector? = null,
    onIconClick: () -> Unit = {},
    onCloseClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = 32.dp)
            .height(56.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopStart),
            onClick = onCloseClick
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = White,
                contentDescription = null
            )
        }

        if (topBarIcon != null) {
            IconButton(
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.TopEnd),
                onClick = onIconClick
            ) {
                Icon(
                    imageVector = topBarIcon,
                    tint = White,
                    contentDescription = null
                )
            }
        }
    }
}
